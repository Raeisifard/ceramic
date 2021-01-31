<template>

</template>

<script>
  import JsonObject from "./data";

  export default {
    name: 'mxWebsphereMq',
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        image: "/src/images/icons48/mq_sidebar.png",
        label: '<img src="/src/images/icons48/mq.png" width="96" height="96">',
        type: "webspheremq",
        name: "WebsphereMQ"
      }
    },
    methods: {
      addSidebarIcon: function(graph, sidebar, label, image, type) {
        let funct = function(graph, evt, cell, x, y) {
          let parent = graph.getDefaultParent();
          let model = graph.getModel();
          let v1 = null;
          model.beginUpdate();
          try {
            v1 = graph.insertVertex(parent, null, label, x, y, 96, 96, type);
            v1.setConnectable(false);
            v1.setType(type);
            v1.setData(new JsonObject());
            // Presets the collapsed size
            v1.geometry.alternateBounds = new mxRectangle(0, 0, 96, 24);
            // Adds the ports at various relative locations
            let port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 12, 12,
              'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-1+10, -4);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Input', 0, 0.75, 12, 12,
              'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(0+10, -4);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Error', 1, 0.25, 12, 12,
              'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-10-10, -4);
            port.direction = "out";
            port = graph.insertVertex(v1, null, 'Result', 1, 0.75, 12, 12,
              'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-11-10, -4);
            port.direction = "out";
          } finally {
            model.endUpdate();
          }
          graph.setSelectionCell(v1);
        };
        let dashStyle = { width: 96, height: 96 };
        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '48px';
        img.style.height = '48px';
        //img.style.marginTop = '10px';
        img.title = 'Drag this to the diagram to create a new vertex';
        sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = `${dashStyle.width}px`;
        dragElt.style.height = `${dashStyle.height}px`;

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      openConfig: function(menu, cell, evt, cx) {
        let cmd = {};
        cmd.style = { width: '500px', height: '335px' };
        cmd.name = "config";
        cmd.title = "IBM MQ Configuration";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      openSetting: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '500px', height: '170px' };
        cmd.name = "setting";
        cmd.title = "IBM MQ Setting (Message count in each reading.)";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      }
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      this.$nextTick(function() {
        that.addSidebarIcon(graph, that.sidebar, that.label, that.image, that.type);
      });
      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() === 'webspheremq') {

          menu.addItem('Config', 'editors/images/config.png', function() {
            that.openConfig(menu, cell, evt, that);
          });
          menu.addItem('Setting', 'editors/images/setting.png', function() {
            that.openSetting(menu, cell, evt, that);
          });
        }
      };
      const oldDeploy = editor.deploy;
      editor.deploy = function(mxCell) {
        let jModel = oldDeploy(mxCell);
        if (jModel) {
          return jModel;
        } else {
          if (mxCell.getType() === 'webspheremq') {
            let webspheremq = {
              type: mxCell.getType(),//type = "webspheremq";
              id: mxCell.getId(),
              data: mxCell.getData(),
              trigger: [],
              input: [],
              error: [],
              result: []
            };
            //Trigger
            if (mxCell.children[ 0 ].edges)
              mxCell.children[ 0 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() !== mxCell.children[ 0 ].getId()) {
                  webspheremq.trigger.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Input
            if (mxCell.children[ 1 ].edges)
              mxCell.children[ 1 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() !== mxCell.children[ 1 ].getId()) {
                  webspheremq.input.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Error
            if (mxCell.children[ 2 ].edges)
              mxCell.children[ 2 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() !== mxCell.children[ 2 ].getId()) {
                  webspheremq.error.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);

            //Result
            if (mxCell.children[ 3 ].edges)
              mxCell.children[ 3 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() !== mxCell.children[ 3 ].getId()) {
                  webspheremq.result.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);

            return webspheremq;
          } else
            return false;
        }
      };
    }
  }
</script>

