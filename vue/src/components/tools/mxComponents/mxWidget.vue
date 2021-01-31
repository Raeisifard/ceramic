<template>

</template>
<script>
  export default {
    name: 'mxWidget',

    props: {
      sidebar: {
        required: true,
      },
      label: String,
      image: String,
      type: String,
      constants: Object
    },
    methods: {
      addSidebarIcon: function(graph, sidebar, label, image, type, constants) {
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
            v1.setData({ 'type': v1.getType(), 'config': constants.config, 'setting': constants.setting });
            // Presets the collapsed size
            v1.geometry.alternateBounds = new mxRectangle(0, 0, alternateWidth, alternateHeight);

            // Adds the ports at various relative locations
            let port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, portWidth, portHeight,
              'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, yOffset);

            port = graph.insertVertex(v1, null, 'Input', 0, 0.75, portWidth, portHeight,
              'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, -4);

            port = graph.insertVertex(v1, null, 'Error', 1, 0.25, portWidth, portHeight,
              'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(xOffset, yOffset);

            port = graph.insertVertex(v1, null, 'Result', 1, 0.75, portWidth, portHeight,
              'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(xOffset, -4);
          } finally {
            model.endUpdate();
          }

          graph.setSelectionCell(v1);
        };
        let dashStyle = {};
        switch (type) {
          case "buffer":
            dashStyle = { width: 60, height: 100 };
            break;
          case "wenspheremq":
            dashStyle = { width: 60, height: 100 };
            break;
          default:
            dashStyle = { width: 120, height: 120 };
            break;
        }
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
      let graph = this.$store.getters.getEditor.graph;
      this.$nextTick(function() {
        that.addSidebarIcon(graph, that.sidebar, that.label, that.image, that.type, that.constants);
      })
    }
  }
</script>

<style scoped>

</style>
