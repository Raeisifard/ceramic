<template>
    <div id="blocklysContainer"></div>
</template>

<script>
  import VueBlockly from './blockly/VueBlockly.vue'
  import { EventBus } from '../../../event-bus.js';
  import JsonObject from "./data";

  export default {
    name: "mxPattern",
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        type: "pattern",
        image: "/src/images/icons48/freemaker.png",
        cell: null,
        iframe: null
      }
    },
    methods: {
      addSidebarIcon: function(graph, sidebar, ctx, image, type) {
        let funct = function(graph, evt, cell, x, y) {
          let parent = graph.getDefaultParent();
          let model = graph.getModel();
          let v1 = null;
          let label = null;
          model.beginUpdate();
          try {
            v1 = graph.insertVertex(parent, null, label, x, y, 200, 240, 'pattern;');
            v1.setConnectable(false);
            v1.setType(type);
            v1.setData(new JsonObject());
            // Presets the collapsed size
            v1.geometry.alternateBounds = new mxRectangle(0, 0, 150, 40);
            let port = graph.insertVertex(v1, null, 'Trigger', 0, 0, 12, 12,
              'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-8, 6);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Input', 0, 0, 12, 12,
              'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-8, 24);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Error', 1, 0, 12, 12,
              'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-3, 6);
            port.direction = "out";
            port = graph.insertVertex(v1, null, 'Result', 1, 0, 12, 12,
              'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-3, 24);
            port.direction = "out";
            v1.vueComponent = ctx.$store.getters.getVueComponentByObject(ctx, v1);
          } finally {
            model.endUpdate();
          }
          graph.setSelectionCell(v1);
          graph.getView().clear(v1, false, false);
          graph.getView().validate();
        };
        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '48px';
        img.style.height = '48px';
        img.title = 'Drag this to the diagram to create a new pattern';
        sidebar.appendChild(img);
        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = '70px';
        dragElt.style.height = '40px';
        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      configureStylesheet: function(graph) {
        let style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_RECTANGLE;
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.RectanglePerimeter;
        style[ mxConstants.STYLE_FILLCOLOR ] = 'transparent';
        //style[ mxConstants.STYLE_FILLCOLOR ] = '#EE82EE';
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_CENTER;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_MIDDLE;
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = false;
        style[ mxConstants.STYLE_OPACITY ] = '0';
        //style[ mxConstants.STYLE_OPACITY ] = '80';
        style[ mxConstants.STYLE_FONTSIZE ] = '12';
        style[ mxConstants.STYLE_FONTSTYLE ] = 0;
        style[ mxConstants.STYLE_IMAGE_WIDTH ] = '48';
        style[ mxConstants.STYLE_IMAGE_HEIGHT ] = '48';
        style[ mxConstants.STYLE_FOLDABLE ] = 1;
        style[ mxConstants.STYLE_RESIZABLE ] = 1;
        //style[mxConstants.VERTEX_SELECTION_DASHED] = false;
        graph.getStylesheet().putCellStyle('pattern', style);
      },
      openEditor: function(menu, cell, evt, cx) {
        let frame = document.createElement('iframe');
        frame.setAttribute('width', '100%');
        frame.setAttribute('height', '100%');
        frame.setAttribute('src', '/#/pattern');
        frame.style.backgroundColor = 'white';
        this.iframe = frame;
        let wnd = new mxWindow('Pattern Editor ver 1.0.0', frame, 0, 0, window.innerWidth, window.innerHeight, false, false);
        wnd.setClosable(true);
        wnd.setImage('/src/images/freemaker20.png');
        wnd.setVisible(true);
        frame.contentWindow.focus();
        let elm = wnd.getElement();
        elm.style.width = '100%';
        elm.style.height = '100%';
        elm.getElementsByTagName('table')[ 0 ].style.width = '100%';
        elm.getElementsByTagName('table')[ 0 ].style.height = '100%';
        this.cell = cell;
      }
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      let graphConvertValueToString = graph.convertValueToString;
      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) &&
          cell.getType() === "pattern" &&
          typeof cell.vueComponent != 'undefined') {
          let geo = cell.getGeometry();
          let blocklyDiv = cell.vueComponent.$el;
          blocklyDiv.style.width = geo.width + 'px';
          blocklyDiv.style.height = geo.height + 'px';
          let blocklyContainer = blocklyDiv.getElementsByClassName("blocklyDiv")[ 0 ];
          blocklyContainer.style.width = geo.width + 'px';
          blocklyContainer.style.height = geo.height - 40 + 'px';
          let blocklyId = `${cell.getType()}.${cell.getId()}`;
          EventBus.$emit(blocklyId, "SVG_RESIZE");
          return blocklyDiv;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() === 'pattern') {

          menu.addItem('Config', 'editors/images/config.png', function() {
            that.openConfig(menu, cell, evt, that);
          });
          menu.addItem('Setting', 'editors/images/setting.png', function() {
            that.openSetting(menu, cell, evt, that);
          });
          menu.addSeparator();
          menu.addItem('Editor', 'editors/images/edit.png', function() {
            that.openEditor(menu, cell, evt, that);
          });
          /* let submenu1 = menu.addItem('Submenu 1', null, null);

           menu.addItem('Subitem 1', null, function() {
             alert('Subitem 1');
           }, submenu1);
           menu.addItem('Subitem 1', null, function() {
             alert('Subitem 2');
           }, submenu1);*/
        }
      };
      this.$nextTick(function() {
        that.configureStylesheet(graph);
        that.addSidebarIcon(graph, that.sidebar, that, that.image, that.type);
      });
      const _pattern = {
        name: "vue_pattern",
        components: {
          VueBlockly
        },
        props: ['cell', 'store'],
        template: '<vue-blockly :cell="cell" :store="store"></vue-blockly>',
      };
      this.$store.commit("ADD_VUE_OBJECT", _pattern);
      window.onmessage = function(e) {
        if (e.data.cmd && e.data.cmd === 'getCellData') {
          that.iframe.contentWindow.postMessage({cmd: 'setCellData', xml: that.cell.getData().xml}, '*');
        } else if (e.data.cmd && e.data.cmd === 'setCellCode') {
          that.cell.data.xml = e.data.xml;
          that.cell.data.code = e.data.code;
          EventBus.$emit(`${that.cell.getType()}.${that.cell.getId()}`, "SVG_REMAKE");
        }
      };
    }
  }
</script>

<style scoped>
    td.mxWindowTitle {
        padding-bottom: 0;
    }
</style>