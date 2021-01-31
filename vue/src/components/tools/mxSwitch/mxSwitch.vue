<template>
</template>

<script>
  import { EventBus } from '../../../event-bus.js';
  //import SwitchButtons from './repo/switch/SwitchButtons.vue';
  //import BlackWhiteYesNo from './repo/switch/BlackWhiteYesNo.vue';
  //import BlueRedYesNoRounded from "./repo/switch/BlueRedYesNoRounded";
  import RedGreenToggleSwitch from "./repo/switch/RedGreenToggleSwitch";
  import GreenGrayOnOffBreak from "./repo/switch/GreenGrayOnOffBreak";
  import BlueSubscribeButtons from './repo/switch/blueSubscribeButtons.vue';
  //import EasyUiRoundedSwitchButtons from './repo/switch/EasyUiRoundedSwitchButtons.vue';
  import OrangeSubscribeButtons from './repo/switch/orangeSubscribeButtons.vue';
  import SwitchButton3d from './repo/switch/SwitchButton3d.vue';
  import dialogConfig from './dialogConfig.vue';
  import JsonObject from "./data";
  import LightGreenToggle from "./repo/relay/LightGreenToggle";
  import IosGreenToggle from "./repo/relay/IosGreenToggle";
  import SkewedGreenToggle from "./repo/relay/SkewedGreenToggle";
  import FlatGreenToggle from "./repo/relay/FlatGreenToggle";
  import RoundCssNavyPushButton from "./repo/push/roundCssNavyPushButton";
  import RoundCssGrayPushButton from "./repo/push/roundCssGrayPushButton";
  import RoundCssBrownPushButton from "./repo/push/roundCssBrownPushButton";
  import RoundCssGreenPushButton from "./repo/push/roundCssGreenPushButton";
  import RoundCssOrangePushButton from "./repo/push/roundCssOrangePushButton";
  import RoundCssPinkPushButton from "./repo/push/roundCssPinkPushButton";
  import RoundCssPurplePushButton from "./repo/push/roundCssPurplePushButton";
  import RoundCssRedPushButton from "./repo/push/roundCssRedPushButton";
  import RoundCssYellowPushButton from "./repo/push/roundCssYellowPushButton";

  export default {
    name: 'mxSwitch',
    components: {
      dialogConfig
    },
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        image: "/src/images/icons48/switch.png",
        type: "switch",
        dialog: null,
        switchType: "SwitchButton",
        initstate: true,
        classname: "rounded",
        readonly: false,
        disabled: false,
        wnd: null,
        switchObject: null,
        graph: null,
        evt: null,
        cell: null,
        x: null,
        y: null
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
      insertSwitch: function(graph, evt, cell, x, y, obj) {
        let parent = graph.getDefaultParent();
        let model = graph.getModel();
        let v1 = null;
        let port = null;
        let config = obj;
        model.beginUpdate();
        try {
          switch (config.kind + '_' + config.switchName) {
            case "trigger_RedGreenToggleSwitch":
              v1 = graph.insertVertex(parent, null, null, x, y, 85, 45, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 80, 45); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-5, -5);
              port.direction = "out";
              break;
            case "trigger_GreenGrayOnOffBreak":
              v1 = graph.insertVertex(parent, null, null, x, y, 105, 35, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 105, 35); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-5, -5);
              port.direction = "out";
              break;
            case "trigger_SwitchButton3d":
              v1 = graph.insertVertex(parent, null, null, x, y, 80, 40, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 70, 40); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-2, -5);
              port.direction = "out";
              break;
            case "trigger_BlueSubscribeButtons":
              v1 = graph.insertVertex(parent, null, null, x, y, 70, 30, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 70, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-2, -5);
              port.direction = "out";
              break;
            case "trigger_OrangeSubscribeButtons":
              v1 = graph.insertVertex(parent, null, null, x, y, 70, 30, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 70, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-2, -5);
              port.direction = "out";
              break;
            case "relay_LightGreenToggle":
              v1 = graph.insertVertex(parent, null, null, x, y, 45, 35, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 45, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-1, -5);
              port.direction = "out";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-8, -5);
              port.direction = "in";
              break;
            case "relay_IosGreenToggle":
              v1 = graph.insertVertex(parent, null, null, x, y, 45, 35, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 45, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-1, -5);
              port.direction = "out";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-8, -5);
              port.direction = "in";
              break;
            case "relay_SkewedGreenToggle":
              v1 = graph.insertVertex(parent, null, null, x, y, 45, 30, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 45, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-1, -5);
              port.direction = "out";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-8, -5);
              port.direction = "in";
              break;
            case "relay_FlatGreenToggle":
              v1 = graph.insertVertex(parent, null, null, x, y, 45, 35, 'switch;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 45, 30); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-1, -5);
              port.direction = "out";
              port = graph.insertVertex(v1, null, 'Input', 0, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-8, -5);
              port.direction = "in";
              break;
            default:
              v1 = graph.insertVertex(parent, null, null, x, y, 45, 45, 'round_push_button_1;');
              v1.geometry.alternateBounds = new mxRectangle(0, 0, 45, 45); // Presets the collapsed size
              port = graph.insertVertex(v1, null, 'Output', 1, 0.50, 10, 10, 'port;image=src/images/dot.gif;spacingLeft=18', true);
              port.geometry.offset = new mxPoint(-3, -5);
              port.direction = "out";
              break;
          }
          v1.setConnectable(false);
          v1.setType(this.type);
          v1.setData(new JsonObject(config.kind));
          v1.data.config = config;
          v1.vueComponent = this.$store.getters.getVueComponentByObject(this, v1, config.switchName);
        } finally {
          model.endUpdate();
        }
        graph.setSelectionCell(v1);
      },
      addSidebarIcon: function(graph, sidebar, ctx, image, type) {
        let that = this;
        // Function that is executed when the image is dropped on
        // the graph. The cell argument points to the cell under
        // the mousepointer if there is one.
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
          ctx.wnd = ctx.showModalWindow(graph, 'Switch Gallery', content, 400, 288);
        };

        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '48px';
        img.style.height = '38px';
        img.title = 'Drag this to the diagram to create a new vertex';
        sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = '70px';
        dragElt.style.height = '40px';

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      closeDialogConfig: function(obj) {
        this.insertSwitch(this.graph, this.evt, this.cell, this.x, this.y, obj);
        this.wnd.destroy();
      },
      configureStylesheet: function(graph) {
        let style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_RECTANGLE;
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.RectanglePerimeter;
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_CENTER;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_MIDDLE;
        //style[ mxConstants.STYLE_GRADIENTCOLOR ] = '#41B9F5';
        //style[ mxConstants.STYLE_FILLCOLOR ] = '#8CCDF5';
        //style[ mxConstants.STYLE_STROKECOLOR ] = '#1B78C8';
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = true;
        style[ mxConstants.STYLE_OPACITY ] = '0';
        style[ mxConstants.STYLE_FONTSIZE ] = '12';
        style[ mxConstants.STYLE_FONTSTYLE ] = 0;
        style[ mxConstants.STYLE_IMAGE_WIDTH ] = '48';
        style[ mxConstants.STYLE_IMAGE_HEIGHT ] = '48';
        style[ mxConstants.STYLE_FOLDABLE ] = 0;
        style[ mxConstants.STYLE_RESIZABLE ] = 0;
        //style[mxConstants.VERTEX_SELECTION_DASHED] = false;
        graph.getStylesheet().putCellStyle('switch', style);
        style = {};
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_ELLIPSE;
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.EllipsePerimeter;
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_CENTER;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_MIDDLE;
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = true;
        style[ mxConstants.STYLE_OPACITY ] = '0';
        style[ mxConstants.STYLE_FONTSIZE ] = '12';
        style[ mxConstants.STYLE_FONTSTYLE ] = 0;
        style[ mxConstants.STYLE_IMAGE_WIDTH ] = '48';
        style[ mxConstants.STYLE_IMAGE_HEIGHT ] = '48';
        style[ mxConstants.STYLE_FOLDABLE ] = 0;
        style[ mxConstants.STYLE_RESIZABLE ] = 0;
        graph.getStylesheet().putCellStyle('round_push_button_1', style);
      },
      getVueComponent: function(cell, ctx) {
        const _switcher = {
          components: {
            SwitchButtons
          },
          props: ['cell', 'store'],
          template: '<switch-buttons :cell="cell" :store="store"></switch-buttons>',
        };
        const ComponentClass = Vue.extend(_switcher);
        let instance = new ComponentClass({
          propsData: { cell: cell, store: ctx.$store }
        });
        instance.$mount(); // pass nothing
        //that.$refs.container.appendChild(instance.$el)
        /*function vueComponent(val) {
          this.vueComponent = val;
        }*/
        class vueComponent {
          constructor(val) {
            this.vueComponent = val;
          }
          static get name() {
            return 'vueComponent';
          }
        }
        return new vueComponent(instance);
      },
      openTriggerSetting: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '600px', height: '400px' };
        cmd.name = "triggerSetting";
        cmd.title = "Trigger Setting";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      openPushSetting: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '600px', height: '300px' };
        cmd.name = "pushSetting";
        cmd.title = "Push Setting";
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      setMessage: function(evt, obj) {
        if (evt === 'SET_SWITCH_CONFIG')
          this.closeDialogConfig(obj);
        else if(evt === 'CLOSE_DIALOG_CONFIG')
          this.wnd.destroy();
      },
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      let graphConvertValueToString = graph.convertValueToString;
      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) && cell.getType() === "switch" && typeof cell.vueComponent != 'undefined') {
          //return cell[cell.getType()].$el;
          return cell.vueComponent.$el;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() == 'switch') {
          if (cell.getData().config.kind === 'trigger')
            menu.addItem('Setting', 'editors/images/setting.png', function() {
              that.openTriggerSetting(menu, cell, evt, that);
            });
          if (cell.getData().config.kind === 'push')
            menu.addItem('Setting', 'editors/images/setting.png', function() {
              that.openPushSetting(menu, cell, evt, that);
            });
        }
      };
      this.$nextTick(function() {
        that.configureStylesheet(graph);
        that.addSidebarIcon(graph, that.sidebar, that, that.image, that.type);
      });
      /* this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_SwitchButtons",
        components: {
          SwitchButtons
        },
        props: ['cell', 'store'],
        template: '<switch-buttons :cell="cell" :store="store"></switch-buttons>',
      });*/
      /*this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BlackWhiteYesNo",
        components: {
          BlackWhiteYesNo
        },
        props: ['cell', 'store'],
        template: '<black-white-yes-no :cell="cell" :store="store"></black-white-yes-no>',
      });*/
      /*this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BlueRedYesNoRounded",
        components: {
          BlueRedYesNoRounded
        },
        props: ['cell', 'store'],
        template: '<blue-red-yes-no-rounded :cell="cell" :store="store"></blue-red-yes-no-rounded>',
      });*/
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RedGreenToggleSwitch",
        components: {
          RedGreenToggleSwitch
        },
        props: ['cell', 'store'],
        template: '<red-green-toggle-switch :cell="cell" :store="store"></red-green-toggle-switch>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_GreenGrayOnOffBreak",
        components: {
          GreenGrayOnOffBreak
        },
        props: ['cell', 'store'],
        template: '<green-gray-on-off-break :cell="cell" :store="store"></green-gray-on-off-break>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_BlueSubscribeButtons",
        components: {
          BlueSubscribeButtons
        },
        props: ['cell', 'store'],
        template: '<blue-subscribe-buttons :cell="cell" :store="store"></blue-subscribe-buttons>',
      });
     /* this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_EasyUiRoundedSwitchButtons",
        components: {
          EasyUiRoundedSwitchButtons
        },
        props: ['cell', 'store'],
        template: '<easy-ui-rounded-switch-buttons :cell="cell" :store="store"></easy-ui-rounded-switch-buttons>',
      });*/
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_OrangeSubscribeButtons",
        components: {
          OrangeSubscribeButtons
        },
        props: ['cell', 'store'],
        template: '<orange-subscribe-buttons :cell="cell" :store="store"></orange-subscribe-buttons>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_SwitchButton3d",
        components: {
          SwitchButton3d
        },
        props: ['cell', 'store'],
        template: '<switch-button3d :cell="cell" :store="store"></switch-button3d>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_LightGreenToggle",
        components: {
          LightGreenToggle
        },
        props: ['cell', 'store'],
        template: '<light-green-toggle :cell="cell" :store="store"></light-green-toggle>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_IosGreenToggle",
        components: {
          IosGreenToggle
        },
        props: ['cell', 'store'],
        template: '<ios-green-toggle :cell="cell" :store="store"></ios-green-toggle>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_SkewedGreenToggle",
        components: {
          SkewedGreenToggle
        },
        props: ['cell', 'store'],
        template: '<skewed-green-toggle :cell="cell" :store="store"></skewed-green-toggle>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_FlatGreenToggle",
        components: {
          FlatGreenToggle
        },
        props: ['cell', 'store'],
        template: '<flat-green-toggle :cell="cell" :store="store"></flat-green-toggle>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssNavyPushButton",
        components: {
          RoundCssNavyPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-navy-push-button :cell="cell" :store="store"></round-css-navy-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssBrownPushButton",
        components: {
          RoundCssBrownPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-brown-push-button :cell="cell" :store="store"></round-css-brown-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssGrayPushButton",
        components: {
          RoundCssGrayPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-gray-push-button :cell="cell" :store="store"></round-css-gray-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssGreenPushButton",
        components: {
          RoundCssGreenPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-green-push-button :cell="cell" :store="store"></round-css-green-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssOrangePushButton",
        components: {
          RoundCssOrangePushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-orange-push-button :cell="cell" :store="store"></round-css-orange-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssPinkPushButton",
        components: {
          RoundCssPinkPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-pink-push-button :cell="cell" :store="store"></round-css-pink-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssPurplePushButton",
        components: {
          RoundCssPurplePushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-purple-push-button :cell="cell" :store="store"></round-css-purple-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssRedPushButton",
        components: {
          RoundCssRedPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-red-push-button :cell="cell" :store="store"></round-css-red-push-button>',
      });
      this.$store.commit("ADD_VUE_OBJECT", {
        name: "vue_RoundCssYellowPushButton",
        components: {
          RoundCssYellowPushButton
        },
        props: ['cell', 'store'],
        template: '<round-css-yellow-push-button :cell="cell" :store="store"></round-css-yellow-push-button>',
      });
    },
    computed: {
      console: () => console,
      window: () => window,
    }
  }
</script>

<style scoped>
    td.mxWindowTitle {
        padding-bottom: 6px;
    }
</style>
