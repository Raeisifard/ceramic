<template>

</template>

<script>
  import JsonObject from "./constants";

  export default {
    name: 'mxWebsite',
    components: {},
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        type: "website",
        image: "/src/images/icons48/earth.png",
        label:
          '<h1 style="margin:0;">Website</h1>' +
          '<br>' +
          '<img src="/src/images/icons48/earth.png" width="48" height="48">' +
          '<br>' +
          '<a href="http://www.jgraph.com" target="_blank">Browse</a>'
      }
    },
    methods: {
      openEditor: function(menu, cell, evt, cx) {
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", "editor");
        cx.$store.getters.getDialog.open();
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
        let dashStyle = { width: 120, height: 120 };
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
        if (cell != null && cell.getType() == 'website') {
          menu.addItem('Editor', 'editors/images/edit.png', function() {
            that.openEditor(menu, cell, evt, that);
          });

          menu.addItem('Item 2', null, function() {
            alert('Item 2');
          });

          menu.addSeparator();

          var submenu1 = menu.addItem('Submenu 1', null, null);

          menu.addItem('Subitem 1', null, function() {
            alert('Subitem 1');
          }, submenu1);
          menu.addItem('Subitem 1', null, function() {
            alert('Subitem 2');
          }, submenu1);
        }
      };
      /*const oldDeploy = editor.deploy;
      editor.deploy = function(mxCell) {
        let jModel = oldDeploy(mxCell);
        if (jModel) {
          return jModel;
        } else {
          if (mxCell.getType() == 'website') {
            let website = {
              type: mxCell.getType(),//type = "website";
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
                  website.trigger.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue());
                }
              }, that);

            //Input
            if (mxCell.children[ 1 ].edges)
              mxCell.children[ 1 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() != mxCell.children[ 1 ].getId()) {
                  website.input.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue());
                }
              }, that);

            //Error
            if (mxCell.children[ 2 ].edges)
              mxCell.children[ 2 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() != mxCell.children[ 2 ].getId()) {
                  website.error.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue());
                }
              }, that);

            //Result
            if (mxCell.children[ 3 ].edges)
              mxCell.children[ 3 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() != mxCell.children[ 3 ].getId()) {
                  website.result.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue());
                }
              }, that);

            //console.dir(mxCell);
            return website;
          } else
            return false;
        }
      };*/
      /*let codec = new mxObjectCodec(new JsonObject());

      codec.encode = function(enc, obj)
      {
        let node = enc.document.createElement('JsonObject');
        mxUtils.setTextContent(node, JSON.stringify(obj));

        return node;
      };

      codec.decode = function(dec, node, into)
      {
        var obj = JSON.parse(mxUtils.getTextContent(node));
        obj.constructor = JsonObject;

        return obj;
      };

      mxCodecRegistry.register(codec);*/
      this.$nextTick(function() {
        that.addSidebarIcon(graph, that.sidebar, that.label, that.image, that.type);
      })
    }
  }
</script>

<style scoped>

</style>
