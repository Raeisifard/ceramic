<template>

    <!-- Creates a container for the graph -->
    <div id="graphContainer">
        <!-- <vx-alert></vx-alert>-->
        <mx-window :cell="getCell" :cmd="getCmd"></mx-window>
    </div>

</template>

<script>
  /*import VxAlert from "@/components/tools/vxAlert.vue";*/

  import MxWindow from "./tools/mxComponents/mxWindow";
  import { EventBus } from "../event-bus";

  export default {
    name: 'Graph',
    /*data() {
      return { cell: null }
    },*/
    components: { MxWindow },
    /*components: { VxAlert },*/
    props: {
      //msg: String
    },
    computed: {
      getRenderMxWindow: function() {
        return this.$store.getters.getRenderMxWindow
      },
      getCell: function() {
        return this.$store.getters.getCell;
      },
      getCmd: function() {
        return this.$store.getters.getCmd;
      }/*,
      getTitle: function() {
        return this.$store.getters.getTitle;
      }*/
    },
    methods: {
      uuidv4: function() {
        return ( [1e7] + -1e3 + -4e3 + -8e3 + -1e11 ).replace(/[018]/g, c =>
          ( c ^ crypto.getRandomValues(new Uint8Array(1))[ 0 ] & 15 >> c / 4 ).toString(16)
        )
      },
      uid: function(length) {
        let result = '';
        let alphanumeric = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
        let alphabet = alphanumeric.substring(0, 26);
        let alphanumericLength = alphanumeric.length;
        result = alphabet.charAt(Math.floor(Math.random() * alphabet.length));
        for (let i = 1; i < length; i++) {
          result += alphanumeric.charAt(Math.floor(Math.random() * alphanumericLength));
        }
        return result;
      },
      configureStylesheet: function(graph) {
        var style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_RECTANGLE;
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.RectanglePerimeter;
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_CENTER;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_MIDDLE;
        style[ mxConstants.STYLE_GRADIENTCOLOR ] = '#41B9F5';
        style[ mxConstants.STYLE_FILLCOLOR ] = '#8CCDF5';
        style[ mxConstants.STYLE_STROKECOLOR ] = '#1B78C8';
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = true;
        style[ mxConstants.STYLE_OPACITY ] = '80';
        style[ mxConstants.STYLE_FONTSIZE ] = '12';
        style[ mxConstants.STYLE_FONTSTYLE ] = 0;
        style[ mxConstants.STYLE_IMAGE_WIDTH ] = '48';
        style[ mxConstants.STYLE_IMAGE_HEIGHT ] = '48';
        graph.getStylesheet().putDefaultVertexStyle(style);

        // NOTE: Alternative vertex style for non-HTML labels should be as
        // follows. This repaces the above style for HTML labels.
        /*var style = new Object();
        style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_LABEL;
        style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
        style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
        style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
        style[mxConstants.STYLE_IMAGE_ALIGN] = mxConstants.ALIGN_CENTER;
        style[mxConstants.STYLE_IMAGE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
        style[mxConstants.STYLE_SPACING_TOP] = '56';
        style[mxConstants.STYLE_GRADIENTCOLOR] = '#7d85df';
        style[mxConstants.STYLE_STROKECOLOR] = '#5d65df';
        style[mxConstants.STYLE_FILLCOLOR] = '#adc5ff';
        style[mxConstants.STYLE_FONTCOLOR] = '#1d258f';
        style[mxConstants.STYLE_FONTFAMILY] = 'Verdana';
        style[mxConstants.STYLE_FONTSIZE] = '12';
        style[mxConstants.STYLE_FONTSTYLE] = '1';
        style[mxConstants.STYLE_ROUNDED] = '1';
        style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
        style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
        style[mxConstants.STYLE_OPACITY] = '80';
        graph.getStylesheet().putDefaultVertexStyle(style);*/

        style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_SWIMLANE;
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.RectanglePerimeter;
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_CENTER;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_TOP;
        style[ mxConstants.STYLE_FILLCOLOR ] = '#FF9103';
        style[ mxConstants.STYLE_GRADIENTCOLOR ] = '#F8C48B';
        style[ mxConstants.STYLE_STROKECOLOR ] = '#E86A00';
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        style[ mxConstants.STYLE_ROUNDED ] = true;
        style[ mxConstants.STYLE_OPACITY ] = '80';
        style[ mxConstants.STYLE_STARTSIZE ] = '30';
        style[ mxConstants.STYLE_FONTSIZE ] = '16';
        style[ mxConstants.STYLE_FONTSTYLE ] = 1;
        graph.getStylesheet().putCellStyle('group', style);

        style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_IMAGE;
        style[ mxConstants.STYLE_FONTCOLOR ] = '#774400';
        style[ mxConstants.STYLE_PERIMETER ] = mxPerimeter.RectanglePerimeter;
        style[ mxConstants.STYLE_PERIMETER_SPACING ] = '6';
        style[ mxConstants.STYLE_ALIGN ] = mxConstants.ALIGN_LEFT;
        style[ mxConstants.STYLE_VERTICAL_ALIGN ] = mxConstants.ALIGN_MIDDLE;
        style[ mxConstants.STYLE_FONTSIZE ] = '10';
        style[ mxConstants.STYLE_FONTSTYLE ] = 2;
        style[ mxConstants.STYLE_IMAGE_WIDTH ] = '10';
        style[ mxConstants.STYLE_IMAGE_HEIGHT ] = '10';
        graph.getStylesheet().putCellStyle('port', style);

        style = graph.getStylesheet().getDefaultEdgeStyle();
        style[ mxConstants.STYLE_LABEL_BACKGROUNDCOLOR ] = '#FFFFFF';
        style[ mxConstants.STYLE_STROKEWIDTH ] = '2';
        style[ mxConstants.STYLE_ROUNDED ] = true;
        style[ mxConstants.STYLE_EDGE ] = mxEdgeStyle.EntityRelation;

        style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_CYLINDER;
        style[ mxConstants.STYLE_GRADIENTCOLOR ] = '#f5d518';
        style[ mxConstants.STYLE_FILLCOLOR ] = '#f5eda4';
        style[ mxConstants.STYLE_STROKECOLOR ] = '#c89d12';
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        graph.getStylesheet().putCellStyle('buffer', style);

        style = new Object();
        style[ mxConstants.STYLE_SHAPE ] = mxConstants.SHAPE_HEXAGON;
        style[ mxConstants.STYLE_GRADIENTCOLOR ] = '';
        style[ mxConstants.STYLE_FILLCOLOR ] = 'rgba(255,255,255,0)';
        style[ mxConstants.STYLE_STROKECOLOR ] = 'rgba(255,255,255,0)';
        style[ mxConstants.STYLE_FONTCOLOR ] = '#000000';
        graph.getStylesheet().putCellStyle('webspheremq', style);

      },
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
      },
      createTask: function(editor) {
        let that = this;
        mxEditor.prototype.createTasks = function(div) {
          let off = 30;
          let headers = {
            cmd: "list",
            uid: that.$store.getters.getGraphId || "DEFAULT",
            name: that.$store.getters.getGraphName
          };
          that.$store.getters.getEb.send('mx.vx', "Get Graph List", headers, (err, res) => {
            if (err == null) {
              //let list = JSON.parse(res.body);
              div.innerHTML = "";
              res.body.forEach((g, i) => {
                mxUtils.linkInvoke(div, Object.keys(g)[ 0 ] + ":" + Object.values(g)[ 0 ], this,
                  'getGraph', Object.keys(g)[ 0 ], off);
                mxUtils.br(div);
                mxUtils.br(div);
              });
            } else {
              mxLog.warn(JSON.stringify(err));
            }
          });
        };
      }
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      // Sets the graph container and configures the editor
      editor.setGraphContainer(this.$el);
      let config = mxUtils.load(
        '/editors/config/keyhandler-commons.xml').getDocumentElement();
      editor.configure(config);

      // Defines the default group to be used for grouping. The
      // default group is a field in the mxEditor instance that
      // is supposed to be a cell which is cloned for new cells.
      // The groupBorderSize is used to define the spacing between
      // the children of a group and the group bounds.
      let group = new mxCell('Group', new mxGeometry(), 'group');
      group.setVertex(true);
      group.setConnectable(false);
      editor.defaultGroup = group;
      editor.groupBorderSize = 20;

      // Disables drag-and-drop into non-swimlanes.
      graph.isValidDropTarget = function(cell, cells, evt) {
        return this.isSwimlane(cell);
      };

      // Disables drilling into non-swimlanes.
      graph.isValidRoot = function(cell) {
        return this.isValidDropTarget(cell);
      };

      // Does not allow selection of locked cells
      graph.isCellSelectable = function(cell) {
        return !this.isCellLocked(cell);
      };

      //Cell click event
      /*graph.addListener(mxEvent.ADD_CELLS, function(sender, evt) {
        /!*let cell = evt.getProperty("cell"); // cell may be null
        if (cell != null) {

        }else{
          //graph.getSelectionModel().clear();
        }*!/
        //evt.consume();
      });*/
      graph.addListener(mxEvent.CLICK, function(sender, evt) {
        let cell = evt.getProperty("cell"); // cell may be null
        if (cell != null) {
          graph.orderCells(false, [cell]);
        } else {
          //graph.getSelectionModel().clear();
        }
        //evt.consume();
      });
      let oldSetLocation = mxWindow.prototype.setLocation;
      mxWindow.prototype.setLocation = function(x, y) {
        let iw = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        let ih = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        x = Math.max(0, x);
        y = Math.max(0, y);
        if (x + this.table.clientWidth > iw) {
          x = Math.max(0, iw - this.table.clientWidth);
        }

        if (y + this.table.clientHeight > ih) {
          y = Math.max(0, ih - this.table.clientHeight);
        }

        if (this.getX() != x || this.getY() != y) {
          oldSetLocation.apply(this, [x, y]);
        }
      };
      // Returns a shorter label if the cell is collapsed and no
      // label for expanded groups
      graph.getLabel = function(cell) {
        let tmp = mxGraph.prototype.getLabel.apply(this, arguments); // "supercall"
        if (this.isCellCollapsed(cell)) {
          if (cell.getType() === "webspheremq")
            tmp = `<h2>IBM MQ</h2>`;

        } else if (this.isCellLocked(cell)) {
          // Returns an empty label but makes sure an HTML
          // element is created for the label (for event
          // processing wrt the parent label)
          return '';
        }
        return tmp;
      };

      /*graph.getLabel = function(cell)
      {
        let label = null;
        if (this.model.isVertex(cell) && cell.getType() === "switch"){
          label = "<SwitchButton inputId=\"s1\"></SwitchButton>";
        }else {
          label = mxGraph.prototype.getLabel.apply(this, arguments);
        }
        return label;
      };*/

      // Disables HTML labels for swimlanes to avoid conflict
      // for the event processing on the child cells. HTML
      // labels consume events before underlying cells get the
      // chance to process those events.
      //
      // NOTE: Use of HTML labels is only recommended if the specific
      // features of such labels are required, such as special label
      // styles or interactive form fields. Otherwise non-HTML labels
      // should be used by not overidding the following function.
      // See also: configureStylesheet.
      graph.isHtmlLabel = function(cell) {
        return !this.isSwimlane(cell);
      };

      // To disable the folding icon, use the following code:
      /*graph.isCellFoldable = function(cell)
      {
          return false;
      }*/

      // Shows a "modal" window when double clicking a vertex.
      graph.dblClick = function(evt, cell) {
        // Do not fire a DOUBLE_CLICK event here as mxEditor will
        // consume the event and start the in-place editor.
        if (this.isEnabled() &&
          !mxEvent.isConsumed(evt) &&
          cell != null &&
          this.isCellEditable(cell)) {
          if (this.model.isEdge(cell) ||
            !this.isHtmlLabel(cell)) {
            this.startEditingAtCell(cell);
          } else {
            /*let content = document.createElement('div');
            content.innerHTML = this.convertValueToString(cell);
            //that.showModalWindow(this, 'Properties', content, 400, 300);
            //that.$refs.d1.open()
            //that.$router.push('/mx-window');
            //that.$store.commit("SET_RENDERMXWINDOW", true);
            //that.cell = cell;
            //Vue.cell = cell;
            that.$store.dispatch("setCell", cell);
            //that.$store.getters.getDialog.center();
            //that.$store.getters.getDialog.hcenter();
            //that.$store.getters.getDialog.vcenter();
            that.$store.getters.getDialog.open();*/
          }
        }

        // Disables any default behaviour for the double click
        mxEvent.consume(evt);
      };
      // Configures automatic expand on mouseover
      graph.popupMenuHandler.autoExpand = true;

      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell == null) {
          menu.addItem('Console', 'editors/images/console.gif', function() {
            editor.execute("toggleConsole");
          });

          menu.addItem('Outline', 'editors/images/outline.gif', function() {
            editor.execute("toggleOutline");
          });
          menu.addSeparator();
          menu.addItem('Sync up', 'src/images/syncup.png', function() {
            alert('Item 1');
          });

          menu.addItem('Sync down', 'src/images/syncdown.png', function() {
            alert('Item 2');
          });

          menu.addSeparator();

          let connection = menu.addItem('Connection', 'src/images/connection.png', null);

          menu.addItem('Start', 'src/images/connection_start.png', function() {
            that.$store.dispatch("setEb");
          }, connection);
          menu.addItem('Stop', 'src/images/connection_stop.png', function() {
            let eb = that.$store.getters.getEb;
            if (eb) {
              eb.enableReconnect(false);
              eb.close();
            }
          }, connection);
          menu.addSeparator();
          menu.addItem('Show', 'src/images/camera.png', function() {
            editor.execute("show");
          });
          menu.addItem('Print', 'src/images/printer.png', function() {
            editor.execute("print");
          });
        }
      };

      // Enables new connections
      graph.setConnectable(true);

      // Adds all required styles to the graph (see below)
      this.configureStylesheet(graph);
      //mxCodecRegistry.getCodec(mxCell).exclude.push('VueComponent');



      this.$store.dispatch("createNewGraph", "DefaultGraph");
     /* mxEditor.prototype.getGraph = function(graphId) {
        let enc = new mxCodec(mxUtils.createXmlDocument());
        let model = editor.graph.getModel();
        let node = enc.encode(model);
        let store = that.$store;
        this.eb = store.getters.getEb;
        let headers = {
          cmd: "get_graph",
          uid: graphId
        };
        this.eb.send('mx.vx', mxUtils.getXml(node), headers, (err, res) => {
          if (err == null) {
            editor.setGraph(res.body, res.headers.graph_id, res.headers.graph_name, res.headers.active);
          } else {
            mxLog.warn(JSON.stringify(err));
          }
        });
      }*/
      mxEditor.prototype.setGraph = function(xmlModel, graph_id, graph_name, active) {
        let xmlDocument = mxUtils.parseXml(xmlModel);
        if (xmlDocument.documentElement != null && xmlDocument.documentElement.nodeName === 'mxGraphModel') {
          EventBus.$off();
          let decoder = new mxCodec(xmlDocument);
          let node = xmlDocument.documentElement;
          that.$store.dispatch("setGraphId", graph_id);
          that.$store.commit("SET_GRAPH_NAME", graph_name);
          that.$store.dispatch("setGraphStatus", active ? 'deployed' : 'undeployed');
          decoder.decode(node, editor.graph.getModel());
          let cell0 = editor.graph.getModel().getCell(0);
          let version = cell0.version;
          that.$store.commit("SET_VERSION", version);
        }
      }
      this.$store.subscribe((mutation, state) => {
        switch (mutation.type) {
          case 'SET_GRAPH_STATE':
            /*console.log('set graph state');
            const graphState = state.graphState;
            if (graphState === 'ready') {
              this.chartData = {};
              EventBus.$on(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}.input`.toLowerCase(), msg => {
                this.adaptor(this.chartData, msg, this.cell.getData().setting);
                this.$data._chart.update();
              });
            }*/
            break;
          case 'SET_GRAPH_STATUS':
            const graphStatus = state.graphStatus
            /*;
            this.chartData = {};
            this.options = this.cell.getData().setting.option;
            this.renderChart(this.chartData, this.options);
            if (graphStatus === 'deployed') {
              EventBus.$on(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}.input`.toLowerCase(), msg => {
                this.adaptor(this.chartData, msg, this.cell.getData().setting);
                //this.$data._chart.update();
                let data = {
                  "datasets": [{
                    "borderColor": "rgb(75, 192, 192)",
                    "data": [65, 59, 80, 81, 56, 55, 40],
                    "fill": false,
                    "label": "Demo",
                    "lineTension": 0.1
                  }
                  ],
                  "labels": ["January", "February", "March", "April", "May", "June", "July"]
                }
                let options = {
                  scales: {
                    yAxes: [{
                      stacked: false
                    }]
                  },
                  elements: {
                    line: {
                      tension: 0 // disables bezier curves
                    }
                  },
                  showLines: true // disable for all datasets
                }
                this.renderChart(data, options);
              });
            }*/
            break;
        }
      });
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #graphContainer {
        box-sizing: initial;
        position: absolute;
        overflow: hidden;
        top: 36px;
        left: 60px;
        bottom: 36px;
        right: 0;
        background-image: url('/editors/images/grid.gif');
        cursor: default;
    }
</style>
