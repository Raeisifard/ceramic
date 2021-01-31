<template>

</template>

<script>
  import dialogConfig from "./dialogConfig";
  import dialogSetting from "./dialogSetting";
  import dialogAdaptor from "./dialogAdaptor";
  //import { EventBus } from '../../../event-bus.js';
  import BasicLineChart from "./components/line/basic_line_chart/BasicLineChart";
  import BasicBarChart from "./components/bar/basic_bar_chart/BasicBarChart";
  import BasicPieChart from "./components/pie/basic_pie_chart/BasicPieChart";
  //import { mapState } from "vuex";
  import Picker from 'vanilla-picker';

  export default {
    name: "mxChart",
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        type: "chart",
        image: "/src/images/icons48/chart.png",
        colorPicker: null,
        fillColor: 'rgba(150,241,255,0.65)',
        wnd: null
      }
    },
    methods: {
      showModalWindow: function(graph, title, content, width, height) {
        let background = document.createElement('div');
        background.style.position = 'absolute';
        background.style.left = '0px';
        background.style.top = '0px';
        background.style.right = '0px';
        background.style.bottom = '0px';
        background.style.background = 'black';
        mxUtils.setOpacity(background, 50);
        document.body.appendChild(background);
        if (mxClient.IS_IE) {
          new mxDivResizer(background);
        }
        let x = Math.max(0, document.body.scrollWidth / 2 - width / 2);
        let y = Math.max(10, ( document.body.scrollHeight ||
          document.documentElement.scrollHeight ) / 2 - height * 2 / 3);
        let wnd = new mxWindow(title, content, x, y, width, height, false, true);
        wnd.setClosable(true);

        // Fades the background out after after the window has been closed
        wnd.addListener(mxEvent.DESTROY, function(evt) {
          graph.setEnabled(true);
          mxEffects.fadeOut(background, 50, true,
            10, 30, true);
        });

        graph.setEnabled(false);
        graph.tooltipHandler.hide();
        wnd.setVisible(true);
        return wnd;
      },
      setMessage: function(evt, obj) {
        if (evt === 'SET_CHART_CONFIG')
          this.closeDialogConfig(obj);
      },
      addSidebarIcon: function(graph, ctx) {
        let that = this;
        let funct = function(graph, evt, cell, x, y) {
          ctx.graph = graph;
          ctx.evt = evt;
          ctx.cell = cell;
          ctx.x = x;
          ctx.y = y;
          let content = document.createElement('div');
          const ComponentClass = Vue.extend({
            name: "DialogConfig",
            props: ["messageHandler"],
            components: {
              dialogConfig
            },
            template: '<dialog-config :message-handler="messageHandler"></dialog-config>',
          });
          let instance = new ComponentClass({
            propsData: {
              messageHandler: that.setMessage,
            }
          });
          instance.$mount(); // pass nothing
          content.append(instance.$el);
          ctx.wnd = ctx.showModalWindow(graph, 'Chart Gallery', content, 410, 275);
        };

        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', ctx.image);
        img.style.width = '48px';
        img.style.height = '38px';
        img.title = 'Drag this to the diagram to create a new vertex';
        ctx.sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = '70px';
        dragElt.style.height = '40px';

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      configureStylesheet: function(graph) {
        let style = {};
        style[ mxConstants.STYLE_GRADIENTCOLOR ] = '';
        style[ mxConstants.STYLE_FILLCOLOR ] = this.fillColor;
        style[ mxConstants.STYLE_STROKECOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = false;
        graph.getStylesheet().putCellStyle('chart', style);
      },
      closeDialogConfig: function(obj) {
        this.insertChart(this.graph, this.evt, this.cell, this.x, this.y, obj);
        this.wnd.destroy();
      },
      insertChart: function(graph, evt, cell, x, y, obj) {
        let parent = graph.getDefaultParent();
        let model = graph.getModel();
        let v1 = new mxCell();
        let port = null;
        let config = obj;
        model.beginUpdate();
        try {
          let port;
          switch (config.kind + '_' + config.chartName) {
            case "line_BasicLineChart":
              v1 = graph.insertVertex(parent, null, null, x, y, 140, 120, 'chart;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40); // Presets the collapsed size
              // Adds the ports at various relative locations
              port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 16, 16,
                'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -8);
              port.direction = "in";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.75, 16, 16,
                'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -4);
              port.direction = "in";
              break;
            case "bar_BasicBarChart":
              v1 = graph.insertVertex(parent, null, null, x, y, 140, 120, 'chart;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40); // Presets the collapsed size
              // Adds the ports at various relative locations
              port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 16, 16,
                'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -8);
              port.direction = "in";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.75, 16, 16,
                'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -4);
              port.direction = "in";
              break;
            case "pie_BasicPieChart":
              v1 = graph.insertVertex(parent, null, null, x, y, 140, 120, 'chart;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 120, 40); // Presets the collapsed size
              // Adds the ports at various relative locations
              port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 16, 16,
                'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -8);
              port.direction = "in";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.75, 16, 16,
                'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
              port.geometry.offset = new mxPoint(-6, -4);
              port.direction = "in";
              break;
            default:
              return;
          }
          v1.setConnectable(false);
          v1.setType(this.type);
          v1.setData(new ( require(`./components/${config.kind}/${config.chartName.replace(/[A-Z]/g, letter => '_' + letter.toLowerCase()).replace(/^_/, "")}/data.js`) ).default());
          v1.vueComponent = this.$store.getters.getVueComponentByObject(this, v1, config.chartName);
        } finally {
          model.endUpdate();
        }
        let state = graph.view.getState(v1);
        state.style[ mxConstants.STYLE_FILLCOLOR ] = v1.getData().config.fillColor || this.fillColor;
        state.shape.apply(state);
        state.shape.redraw();
        graph.setSelectionCell(v1);
      },
      settingWindow: function(cell, evt) {
        if (cell != null && cell.getType() === 'chart') {
          if (this.wnd) {
            this.wnd.destroy();
            this.wnd = null;
          }
          let prop = { cell: cell, store: this.$store, wnd: this.wnd }
          let vueObj = {
            name: "vue_dialogSetting",
            components: {
              dialogSetting
            },
            props: ['prop'],
            template: '<dialog-setting :prop="prop"></dialog-setting>',
          }
          const ComponentClass = Vue.extend(vueObj);
          let instance = new ComponentClass({
            propsData: {
              prop: prop
            }
          });
          instance.$mount();

          let h = 300;
          let w = 500;
          const x = cell.geometry.x;
          const y = cell.geometry.y;
          this.wnd = new mxWindow(`#${cell.getId()} Chart setting`, instance.$el, x, y, w, h, false, true);
          this.wnd.x = x;
          this.wnd.y = y;
          this.wnd.setMaximizable(true);
          this.wnd.setScrollable(false);
          this.wnd.setResizable(true);
          this.wnd.setClosable(true);
          this.wnd.setVisible(true);
          this.wnd.cellID = cell.getId();
          prop.wnd = this.wnd;
        }
      },
      adaptorWindow: function(cell, evt) {
        let that = this;
        if (cell != null && cell.getType() === 'chart') {
          if (this.wnd) {
            this.wnd.destroy();
            this.wnd = null;
          }
          let prop = { cell: cell, store: this.$store, wnd: this.wnd, width: '100%', height: '100%' }
          let vueObj = {
            name: "vue_dialogAdaptor",
            components: {
              dialogAdaptor
            },
            props: ['prop'],
            template: '<dialog-adaptor :prop="prop"></dialog-adaptor>',
          }
          const ComponentClass = Vue.extend(vueObj);
          let instance = new ComponentClass({
            propsData: {
              prop: prop
            }
          });
          instance.$mount();
          let w = 500;
          let h = 300;
          const x = cell.geometry.x;
          const y = cell.geometry.y;
          this.wnd = new mxWindow(`#${cell.getId()} ${cell.getData().config.kind.replace(/(^\w|\s\w)/g, m => m.toUpperCase())} chart adaptor editor (${cell.getData().config.chartName})`, instance.$el, x, y, w, h, false, true);
          this.wnd.x = x;
          this.wnd.y = y;
          this.wnd.setMaximizable(true);
          this.wnd.setScrollable(false);
          this.wnd.setResizable(true);
          this.wnd.setClosable(true);
          this.wnd.setVisible(true);
          this.wnd.cellID = cell.getId();
          this.wnd.addListener(mxEvent.RESIZE_END, function(e) {
            prop.width = that.wnd.table.clientWidth;
            prop.height = that.wnd.table.clientHeight;
          });
          prop.wnd = this.wnd;
        }
      }
    },
    mounted() {
      let that = this;
      let wnd = null;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      let graphConvertValueToString = graph.convertValueToString;

      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) &&
          cell.getType() === "chart" &&
          typeof cell.vueComponent != 'undefined') {
          return cell.vueComponent.$el;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
      this.$nextTick(function() {
        that.configureStylesheet(graph);
        that.addSidebarIcon(graph, that);
      });
    },
    created() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BasicLineChart",
        components: {
          BasicLineChart
        },
        props: ['cell', 'store'],
        template: '<basic-line-chart :styles="cStyle" :cell="cell" :store="store"></basic-line-chart>',
        computed: {
          cStyle() {
            if (typeof this.cell.geometry == 'undefined')
              return null;
            return {
              height: `${this.cell.geometry.height}px`,
              width: `${this.cell.geometry.width}px`,
            }
          }
        },
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BasicBarChart",
        components: {
          BasicBarChart
        },
        props: ['cell', 'store'],
        template: '<basic-bar-chart :styles="cStyle" :cell="cell" :store="store"></basic-bar-chart>',
        computed: {
          cStyle() {
            if (typeof this.cell.geometry == 'undefined')
              return null;
            return {
              height: `${this.cell.geometry.height}px`,
              width: `${this.cell.geometry.width}px`,
            }
          }
        },
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BasicPieChart",
        components: {
          BasicPieChart
        },
        props: ['cell', 'store'],
        template: '<basic-pie-chart :styles="cStyle" :cell="cell" :store="store"></basic-pie-chart>',
        computed: {
          cStyle() {
            if (typeof this.cell.geometry == 'undefined')
              return null;
            return {
              height: `${this.cell.geometry.height}px`,
              width: `${this.cell.geometry.width}px`,
            }
          }
        },
      });

      function mxVertexToolHandler(state) {
        mxVertexHandler.apply(this, arguments);
      }

      mxVertexToolHandler.prototype = new mxVertexHandler();
      mxVertexToolHandler.prototype.constructor = mxVertexToolHandler;

      mxVertexToolHandler.prototype.domNode = null;

      mxVertexToolHandler.prototype.init = function() {
        mxVertexHandler.prototype.init.apply(this, arguments);

        this.domNode = document.createElement('div');
        this.domNode.style.position = 'absolute';
        this.domNode.style.whiteSpace = 'nowrap';
        this.domNode.style.display = 'flex';
        this.domNode.style.flexDirection = 'column';

        function createImage(src) {
          let img = document.createElement('div');
          img.style.backgroundImage = 'url(' + src + ')';
          img.style.backgroundPosition = 'center';
          img.style.backgroundRepeat = 'no-repeat';
          img.style.display = 'flex';
          img.style.margin = '3px';
          return img;
        }

        // Setting
        let img = mxUtils.createImage('editors/images/json.png');
        img.setAttribute('title', 'Option');
        img.style.cursor = 'pointer';
        img.style.width = '16px';
        img.style.height = '16px';
        img.style.margin = '3px';
        mxEvent.addListener(img, 'click',
          mxUtils.bind(this, function(evt) {
            that.settingWindow(this.state.cell, evt);
            mxEvent.consume(evt);
          })
        );
        this.domNode.appendChild(img);

        // Adaptor
        img = mxUtils.createImage('editors/images/edit.png');
        img.setAttribute('title', 'Adaptor');
        img.style.cursor = 'pointer';
        img.style.width = '16px';
        img.style.height = '16px';
        img.style.margin = '3px';
        mxEvent.addGestureListeners(img,
          mxUtils.bind(this, function(evt) {
            that.adaptorWindow(this.state.cell, evt);
            mxEvent.consume(evt);
          })
        );
        this.domNode.appendChild(img);

        // Color picker
        img = createImage('editors/images/color.png');
        img.setAttribute('title', 'Color');
        img.style.cursor = 'pointer';
        img.style.width = '16px';
        img.style.height = '16px';
        img.style.margin = '3px';
        mxEvent.addListener(img, 'click',
          mxUtils.bind(this, function(evt) {
            //this.graph.removeCells([this.state.cell]);
            //if (!( that.colorPicker && that.colorPicker.show() ))
            if (that.colorPicker !== null) {
              that.colorPicker.destroy();
              that.colorPicker = null;
            }
            that.colorPicker = new Picker({
              parent: img,
              popup: 'top',
              color: this.state.style[ mxConstants.STYLE_FILLCOLOR ],
              onChange: mxUtils.bind(this, function(color) {
                that.fillColor = color.rgbaString;
                this.state.style[ mxConstants.STYLE_FILLCOLOR ] = that.fillColor;
                this.state.shape.apply(this.state);
                this.state.shape.redraw();
              }),
              onDone: mxUtils.bind(this, function(color) {
                that.fillColor = color.rgbaString;
                this.state.cell.getData().config.fillColor = that.fillColor;
                this.state.cell.style = this.state.cell.style + 'fillColor=' + that.fillColor;
              })
            });
            that.colorPicker.openHandler();
            mxEvent.consume(evt);
          })
        );
        this.domNode.appendChild(img);

        /* // Move
         var img = createImage('images/plus.png');
         img.setAttribute('title', 'Move');
         img.style.cursor = 'move';
         img.style.width = '16px';
         img.style.height = '16px';
         mxEvent.addGestureListeners(img,
           mxUtils.bind(this, function(evt)
           {
             this.graph.graphHandler.start(this.state.cell,
               mxEvent.getClientX(evt), mxEvent.getClientY(evt));
             this.graph.graphHandler.cellWasClicked = true;
             this.graph.isMouseDown = true;
             this.graph.isMouseTrigger = mxEvent.isMouseEvent(evt);
             mxEvent.consume(evt);
           })
         );
         this.domNode.appendChild(img);
 */
        /* // Connect
         var img = createImage('images/check.png');
         img.setAttribute('title', 'Connect');
         img.style.cursor = 'pointer';
         img.style.width = '16px';
         img.style.height = '16px';
         mxEvent.addGestureListeners(img,
           mxUtils.bind(this, function(evt)
           {
             var pt = mxUtils.convertPoint(this.graph.container,
               mxEvent.getClientX(evt), mxEvent.getClientY(evt));
             this.graph.connectionHandler.start(this.state, pt.x, pt.y);
             this.graph.isMouseDown = true;
             this.graph.isMouseTrigger = mxEvent.isMouseEvent(evt);
             mxEvent.consume(evt);
           })
         );
         this.domNode.appendChild(img);
 */
        this.graph.container.appendChild(this.domNode);
        this.redrawTools();
      };

      mxVertexToolHandler.prototype.redraw = function() {
        mxVertexHandler.prototype.redraw.apply(this);
        this.redrawTools();
      };

      mxVertexToolHandler.prototype.redrawTools = function() {
        if (this.state != null && this.domNode != null) {
          let dy = ( mxClient.IS_VML && document.compatMode == 'CSS1Compat' ) ? 20 : 4;
          this.domNode.style.left = ( this.state.x + this.state.width + 2 ) + 'px';
          this.domNode.style.top = ( this.state.y + dy ) + 'px';
        }
      };

      mxVertexToolHandler.prototype.destroy = function(sender, me) {
        mxVertexHandler.prototype.destroy.apply(this, arguments);

        if (this.domNode != null) {
          this.domNode.parentNode.removeChild(this.domNode);
          this.domNode = null;
        }
      };

      graph.createHandler = function(state) {
        if (state != null &&
          this.model.isVertex(state.cell) && state.cell.getType() === 'chart') {
          return new mxVertexToolHandler(state);
        }
        return mxGraph.prototype.createHandler.apply(this, arguments);
      };
    },
    computed: {
      console: () => console,
      window: () => window,
    }
  }
</script>

<style scoped>

</style>