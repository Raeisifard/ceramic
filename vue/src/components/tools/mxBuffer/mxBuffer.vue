<template>

</template>

<script>
  import mxWidget from '../mxComponents/mxWidget.vue';
  import JsonObject from "./data";

  export default {
    name: 'mxBuffer',
    components: {
      mxWidget
    },
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        type: "buffer",
        image: "/src/images/icons48/buffer.png",
        label:
          '<h1 style="margin: 1px 0 7px 0;">Buffer</h1>' +
          '<img src="/src/images/icons48/buffer.png" width="48" height="48">',
      }
    },
    methods: {
      openConfig: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '380px', height: '380px' };
        cmd.name = "config";
        cmd.title = "Buffer Configuration";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      addSidebarIcon: function(graph, sidebar, label, image, type) {
        // Function that is executed when the image is dropped on
        // the graph. The cell argument points to the cell under
        // the mousepointer if there is one.

        let funct = function(graph, evt, cell, x, y) {
          let parent = graph.getDefaultParent();
          let model = graph.getModel();

          let v1 = null;

          model.beginUpdate();
          try {
            // NOTE: For non-HTML labels the image must be displayed via the style
            // rather than the label markup, so use 'image=' + image for the style.
            // as follows: v1 = graph.insertVertex(parent, null, label,
            // pt.x, pt.y, 120, 120, 'image=' + image);
            let width = 120, height = 120, xOffset = -8, yOffset = -8, portWidth = 16, portHeight = 16,
              alternateWidth = 120, alternateHeight = 40;
            switch (type) {
              case "buffer":
                width = 60;
                height = 100;
                xOffset = -6;
                yOffset = 8;
                portWidth = 12;
                portHeight = 12;
                alternateWidth = 60;
                alternateHeight = 30;
                break;
              case "webspheremq":
                width = 96;
                height = 96;
                xOffset = -8;
                yOffset = 4;
                portWidth = 12;
                portHeight = 12;
                alternateWidth = 60;
                alternateHeight = 30;
            }
            v1 = graph.insertVertex(parent, null, label, x, y, width, height, type);
            v1.setConnectable(false);
            v1.setType(type);
            v1.setData(new JsonObject());
            // Presets the collapsed size
            v1.geometry.alternateBounds = new mxRectangle(0, 0, alternateWidth, alternateHeight);

            // Adds the ports at various relative locations
            let port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, portWidth, portHeight,
              'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, yOffset);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Input', 0, 0.75, portWidth, portHeight,
              'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, -4);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Error', 1, 0.25, portWidth, portHeight,
              'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(xOffset, yOffset);
            port.direction = "out";
            port = graph.insertVertex(v1, null, 'Result', 1, 0.75, portWidth, portHeight,
              'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(xOffset, -4);
            port.direction = "out";
          } finally {
            model.endUpdate();
          }

          graph.setSelectionCell(v1);
        };
        let dashStyle = { width: 60, height: 100 };
        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '48px';
        img.style.height = '48px';
        img.title = 'Drag this to the diagram to create a new vertex';
        sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = `${dashStyle.width}px`;
        dragElt.style.height = `${dashStyle.height}px`;

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      }
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() == 'buffer') {
          menu.addItem('Config', 'editors/images/config.png', function() {
            that.openConfig(menu, cell, evt, that);
          });
        }
      };
      const oldDeploy = editor.deploy;
      editor.deploy = function(mxCell) {
        let jModel = oldDeploy(mxCell);
        if (jModel) {
          return jModel;
        } else {
          if (mxCell.getType() == 'buffer') {
            let buffer = {
              type: mxCell.getType(),//type = "buffer";
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
                if (edge.source.getId() != mxCell.children[ 0 ].getId()) {
                  buffer.trigger.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Input
            if (mxCell.children[ 1 ].edges)
              mxCell.children[ 1 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() != mxCell.children[ 1 ].getId()) {
                  buffer.input.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Error
            if (mxCell.children[ 2 ].edges)
              mxCell.children[ 2 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() != mxCell.children[ 2 ].getId()) {
                  buffer.error.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);

            //Result
            if (mxCell.children[ 3 ].edges)
              mxCell.children[ 3 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() != mxCell.children[ 3 ].getId()) {
                  buffer.result.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);
            return buffer;
          } else
            return false;
        }
      };
      this.$nextTick(function() {
        that.addSidebarIcon(graph, that.sidebar, that.label, that.image, that.type);
      })
    },
    created() {
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      // Returns a shorter label if the cell is collapsed and no
      // label for expanded groups
      let graphConvertValueToString = graph.convertValueToString;
      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) && cell.getType() == "buffer" && this.isCellCollapsed(cell)) {
          return `<h1 style="margin-top: 20px;">Buffer</h1>`;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
    }
  }
</script>

<style scoped>

</style>
