<template>

</template>

<script>
  //import mxWidget from '@/components/tools/mxComponents/mxWidget.vue';
  import chart from './chartComponents/Chart.vue'
  import { EventBus } from "../../../event-bus";
  //import * as constants from './data.js';
  import JsonObject from "./data";
  //import JavascriptTemplate from '!!raw-loader!./chartComponents/adaptorTemplate/javascript.txt';

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
        label:
          '<h1 style="margin:0;">Chart</h1><br>' +
          '<img src="/src/images/icons48/chart2.png" width="78" height="64">',
        //data: new JsonObject()
      }
    },
    computed: {
      /* ...mapGetters([
           'graphStatus', 'graphState', 'graphId'
       ]),*/
      isGraphStateReady: function() {
        return this.$store.getters.graphState === 'ready';
      }
      /*selectChart: function() {
        switch (this.chartType) {
          case "line":
            return LineChart;
          case "bar":
            return BarChart;
          case "pie":
            return PieChart;
          default:
            return LineChart;
        }
      }*/
    },
    methods: {
      addSidebarIcon: function(graph, sidebar, ctx, image, type) {
        // Function that is executed when the image is dropped on
        // the graph. The cell argument points to the cell under
        // the mousepointer if there is one.
        let label = this.label;
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
            let width = 140, height = 120, xOffset = -8, yOffset = -8, portWidth = 16, portHeight = 16,
              alternateWidth = 120, alternateHeight = 40;
            v1 = graph.insertVertex(parent, null, label, x, y, width, height, type);
            v1.setConnectable(false);
            v1.setType(type);
            v1.setValue(ctx.label);
            /*v1.setData({
              'chartType': 'line-chart',
              'code': JavascriptTemplate,
              'type': v1.getType(),
              'enable': false
            });*/
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
            /* port = graph.insertVertex(v1, null, 'Error', 1, 0.25, portWidth, portHeight,
               'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
             port.geometry.offset = new mxPoint(xOffset, yOffset);

             port = graph.insertVertex(v1, null, 'Result', 1, 0.75, portWidth, portHeight,
               'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
             port.geometry.offset = new mxPoint(xOffset, -4);*/
            v1.vueComponent = ctx.$store.getters.getVueComponentByObject(ctx, v1);
            //instance.$mount(); // pass nothing
            //v1[type] = ctx.getVueComponent(v1, ctx);
          } finally {
            model.endUpdate();
          }

          graph.setSelectionCell(v1);
        };

        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '48px';
        img.style.height = '48px';
        //img.style.marginTop = '5px';
        img.title = 'Drag this to the diagram to create a new vertex';
        sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = '140px';
        dragElt.style.height = '120px';

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      getVueComponent: function(cell, ctx) {
        const _chart = {
          name: "vue_chart",
          components: {
            chart
          },
          props: ['cell', 'store'],

          template: '<chart :cell="cell" :store="store" />',
        };
        const ComponentClass = Vue.extend(_chart);
        let instance = new ComponentClass({
          propsData: {
            cell: cell,
            store: ctx.$store
          }
        });
        instance.$mount(); // pass nothing
        function vueComponent(val) {
          this.vueComponent = val;
        }

        return new vueComponent(instance);
      },
      /* openEditor: function(menu, cell, evt, cx) {
         let cmd = {};
         cmd.style = { width: '50%', height: '50%' };
         cmd.name = "editor";
         cmd.title = "Chart Data Function Editor";
         cx.$store.dispatch("setCell", cell);
         cx.$store.commit("SET_CMD", cmd);
         window.easyDialog.open();
         window.easyDialog.moveToTop();
         window.easyDialog.center();
       },*/
      openConfig: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '420px', height: 'unset' };
        cmd.name = "config";
        cmd.title = "Chart Configuration";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      }
    },
    /*created() {
      let that = this;
      let redrawLabelShape = mxCellRenderer.prototype.redrawLabelShape;
      mxCellRenderer.prototype.redrawLabelShape = function(shape) {
        if (shape.state.cell.getType() === "chart" && shape.state.cell.getData.enable && shape.state.cell.vueComponent.$el &&
          shape.state.cell.vueComponent.vueComponent.$el.getElementsByTagName("canvas").length > 0) {
          let canvas = shape.state.cell.vueComponent.vueComponent.$el.getElementsByTagName("canvas")[ 0 ];
          let div = shape.state.cell.vueComponent.vueComponent.$el;
          let cell = shape.state.cell;
          //canvas.width = cell.geometry.width;
          //canvas.height = cell.geometry.height;
          //that.$data._chart.canvas.width = cell.geometry.width + "px";
          //that.$data._chart.canvas.height = cell.geometry.height + "px";
          div.style.width = `${cell.geometry.width}px`;
          div.style.height = `${cell.geometry.height}px`;
        }
        shape.redraw();
      };
    },*/
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() == 'chart') {
          menu.addItem('Config', 'editors/images/config.png', function() {
            that.openConfig(menu, cell, evt, that);
          });
          menu.addSeparator();
          menu.addItem('Editor', 'editors/images/edit.png', function() {
            that.openEditor(menu, cell, evt, that);
          });
        }
      };
      let graphConvertValueToString = graph.convertValueToString;
      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) &&
          cell.getType() === "chart" &&
          typeof cell.vueComponent != 'undefined' &&
          //that.$store.getters.getGraphState === 'ready') {
          that.$store.getters.graphStatus === 'deployed') {
          return cell.vueComponent.$el;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
      const oldDeploy = editor.deploy;
      editor.deploy = function(mxCell) {
        let jModel = oldDeploy(mxCell);
        if (jModel) {
          return jModel;
        } else {
          if (mxCell.getType() == 'chart') {
            let chart = {
              type: mxCell.getType(),//type = "chart";
              id: mxCell.getId(),
              data: mxCell.getData(),
              trigger: [],
              input: []
            };
            //Trigger
            if (mxCell.children[ 0 ].edges)
              mxCell.children[ 0 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() != mxCell.children[ 0 ].getId()) {
                  chart.trigger.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Input
            if (mxCell.children[ 1 ].edges)
              mxCell.children[ 1 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() != mxCell.children[ 1 ].getId()) {
                  chart.input.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);
            return chart;
          } else
            return false;
        }
      };
      EventBus.$on("mx.set_vue_component", function(model) {
        model.beginUpdate();
        try {
          model.getChildVertices(model.root.children[ 0 ]).forEach(c => {
            if (c.getType() === 'chart') {
              c.vueComponent = that.getVueComponent(c, that);
            }
          });
        } finally {
          graph.refresh(model.root.children[ 0 ]);
          model.endUpdate();
        }
      });
      this.$nextTick(function() {
        that.addSidebarIcon(graph, that.sidebar, that, that.image, that.type);
      });
      const _chart = {
        name: "vue_chart",
        components: {
          chart
        },
        props: ['cell', 'store'],

        template: '<chart :cell="cell" :store="store" />',
      };
      this.$store.commit("ADD_VUE_OBJECT", _chart);
    }
  }
</script>
