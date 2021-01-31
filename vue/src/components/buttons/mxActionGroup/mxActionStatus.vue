<template>
  <!--<mx-button :editor="editor" action="deploy" label="Deploy"
             image="/src/images/deploy.png"></mx-button>-->
  <span :class="{open: open, 'x-split-button':true}" @click.stop>
     <!-- <mx-button class="x-button x-button-main" :editor="editor" action="deploy" label="Deploy"
                 image="/src/images/deploy.png"></mx-button>-->
        <button class="x-button x-button-drop" @click="open = addOpenClass()">&#9660;</button>
      <button class="status-button" :class="getConnectionState" type="button" disabled>
        <img v-if="image" :src="image"/>
        {{ action }}
      </button>

      <ul class="x-button-drop-menu" @click="open = false">
          <li>
          <a @click.prevent="newGraph">New</a>
        </li>
        <li>
          <a @click.prevent="deploy">Deploy</a>
        </li>
        <li>
          <a @click.prevent="undeploy">Undeploy</a>
        </li>
        <li>
          <a @click.prevent="redeploy">Redeploy</a>
        </li>
          <mx-open-xml-graph :max=1></mx-open-xml-graph>
          <mx-import-button></mx-import-button>
          <mx-export-button></mx-export-button>
        <li>
          <a @click.prevent="showList">List</a>
        </li>
      </ul>
        <graph-list v-if="showGraphList" @closeGraphList="showList"></graph-list>
    </span>
</template>

<script>
import mxButton from '@/components/buttons/mxComponents/mxButton.vue';
import mxOpenXmlGraph from '@/components/buttons/mxActionGroup/mxOpenXMLGraph.vue';
import { EventBus } from '../../../event-bus.js';
import MxImportButton from "./mxImport/mxImportButton";
import MxExportButton from "./mxExport/mxExportButton";
import { CONNECTION_STATUS } from "@/store/constants.js";
import Graph from "../../Graph";
import GraphList from "./tools/GraphList";

class JsonCodec extends mxObjectCodec {
  constructor() {
    super((value) => {
    });
  }

  encode(value) {
    const xmlDoc = mxUtils.createXmlDocument();
    const newObject = xmlDoc.createElement("Object");
    for (let prop in value) {
      newObject.setAttribute(prop, value[ prop ]);
    }
    return newObject;
  }

  decode(model) {
    return Object.keys(model.cells).map(
        (iCell) => {
          const currentCell = model.getCell(iCell);
          return ( currentCell.value !== undefined ) ? currentCell : null;
        }
    ).filter((item) => ( item !== null ));
  }
}

export default {
  name: "mxDeployButton",
  components: {
    Graph,
    MxExportButton,
    MxImportButton,
    mxButton,
    mxOpenXmlGraph,
    GraphList
  },
  data: function() {
    return {
      action: "Undeployed",
      open: false,
      editor: this.$store.getters.getEditor,
      eb: this.$store.getters.getEb,
      openXmlGraph: false,
      image: "/src/images/deploy.png",
      stateClass: "closed",
      showGraphList: false
    }
  },
  mounted() {
    let that = this;
    this.editor.addAction('deploy', function(editor, cell) {
      let enc = new mxCodec(mxUtils.createXmlDocument());
      let model = editor.graph.getModel();
      let node = enc.encode(model);
      let store = that.$store;
      this.eb = store.getters.getEb;
      //let headers = { cmd: "deploy", name: model.getCell(0).name, uid: model.getCell(0).uid};
      let headers = { cmd: "deploy", name: store.getters.getGraphName, uid: store.getters.getGraphId };
      this.eb.send('mx.vx', mxUtils.getXml(node), headers, (err, res) => {
        if (err == null) {
          editor.setGraph(res.body, res.headers.graph_id, res.headers.graph_name, res.headers.active);
          //store.dispatch("setGraphStatus", "deployed");
        } else {
          mxLog.warn("There is some error in deploying graph!");
          console.dir(err);
        }
      });
    });
    this.editor.addAction('undeploy', function(editor, cell) {
      let store = that.$store;
      this.eb = store.getters.getEb;
      let headers = { cmd: "undeploy", name: store.getters.getGraphName, uid: store.getters.getGraphId };
      this.eb.send('mx.vx', "Undeploy the Graph", headers, (err, res) => {
        if (err == null) {
          store.dispatch("setGraphStatus", "undeployed");
        } else {
          mxLog.warn("There is some error in undeploying graph!");
          console.dir(err);
        }
      });
    });
    this.editor.addAction('redeploy', function(editor, cell) {
      let enc = new mxCodec(mxUtils.createXmlDocument());
      let model = editor.graph.getModel();
      let node = enc.encode(model);
      let store = that.$store;
      this.eb = store.getters.getEb;
      let headers = { cmd: "redeploy", name: store.getters.getGraphName, uid: store.getters.getGraphId };
      this.eb.send('mx.vx', mxUtils.getXml(node), headers, (err, res) => {
        if (err == null) {
          editor.setGraph(res.body, res.headers.graph_id, res.headers.graph_name, res.headers.active);
        } else {
          mxLog.warn("There is some error in redeploying graph!");
          console.dir(err);
        }
      });
    });
    this.editor.addAction('get_graph', function(editor, cell, graphId) {
      let enc = new mxCodec(mxUtils.createXmlDocument());
      let model = editor.graph.getModel();
      let node;
      try {
        node = enc.encode(model);
      } catch (e) {
        console.dir(e);
        return;
      }
      let store = that.$store;
      this.eb = store.getters.getEb;
      let headers = { cmd: "get_graph", uid: graphId };
      this.eb.send('mx.vx', mxUtils.getXml(node), headers, (err, res) => {
        if (err == null) {
          editor.setGraph(res.body, res.headers.graph_id, res.headers.graph_name, res.headers.active);
          //store.dispatch("setGraphStatus", "deployed");
        } else {
          mxLog.warn("There is some error in getting graph!");
          console.dir(err);
        }
      });
    });
    document.body.onclick = function() {
      that.open = false;
    };
    /* let splitBtn = this.$refs.x-split-button;

     this.$refs.button.x-button-drop.on('click', function() {
       if (!splitBtn.hasClass('open'))
         splitBtn.addClass('open');
     });

     $('.x-split-button').click(function(event){
       event.stopPropagation();
     });

     $('html').on('click',function() {
       if (splitBtn.hasClass('open'))
         splitBtn.removeClass('open');
     });*/
    this.$store.dispatch("setEb");
  },
  methods: {
    deploy() {
      this.editor.execute("deploy");
    },
    undeploy() {
      this.editor.execute("undeploy");
    },
    redeploy() {
      this.editor.execute("redeploy");
    },
    newGraph() {
      this.$store.dispatch("createNewGraph", "NewGraph");
    },
    openGraph() {
      this.openXmlGraph = true;
    },
    showList() {
      this.showGraphList = !this.showGraphList;
    },
    addOpenClass() {
      return this.open = !this.open;
    },
    getJsonModel() {
      const encoder = new JsonCodec();
      //encoder.exclude.push('vueComponent').push('data').push('dataset');
      return encoder.decode(this.editor.graph.getModel());//jsonModel
    },

    prettyXmlModel() {
      let enc = new mxCodec(mxUtils.createXmlDocument());
      let node = enc.encode(this.editor.graph.getModel());
      return mxUtils.getPrettyXml(node);
    },
    stringifyWithoutCircular(json) {
      return JSON.stringify(
          json,
          (key, value) => {
            if (( key === 'parent' || key == 'source' || key == 'target' ) && value !== null) {
              return value.id;
            } else if (key === 'value' && value !== null && value.localName) {
              let results = {};
              Object.keys(value.attributes).forEach(
                  (attrKey) => {
                    const attribute = value.attributes[ attrKey ];
                    results[ attribute.nodeName ] = attribute.nodeValue;
                  }
              );
              return results;
            }
            return value;
          },
          4
      );
    }

  },
  computed: {
    getConnectionState: function() {
      if (!this.$store.getters.getEb)
        return "closed";
      let state = this.$store.getters.getEb.state;
      switch (state) {
        case CONNECTION_STATUS.CONNECTING:
          this.stateClass = "connecting";
          break;
        case CONNECTION_STATUS.OPEN:
          this.stateClass = "open";
          break;
        case CONNECTION_STATUS.CLOSING:
          this.stateClass = "closing";
          break;
        case CONNECTION_STATUS.CLOSED:
          this.stateClass = "closed";
          break;
      }
      return this.stateClass;
    }
  }
}
</script>

<style scoped>
.x-split-button {
  border-width: 0;
  /* position: relative;
   display: block;
   text-align: left;*/
  /*margin-top: 20px;*/
}

.x-button {
  /*  position: relative;
    margin: 0;*/
  font-size: 10px;
  /* float: left;
   outline: none;
   !*line-height: 27px;*!
   background: #F2F2F2;
   border: 1px solid #E0E0E0;
   box-shadow: 1px 1px 2px #E0E0E0;*/
}

.x-button:hover {
  cursor: pointer;
  /*background: #E0E0E0;*/
}

.x-button:active {
  background: #D3D3D3;
}

.x-button.x-button-drop {
  /* border-left: 0;
   height: 18px;*/
  padding: 1px 0px;
  top: 2px;
  position: relative;
  height: 23px;
  font-size: 14px;
}

.open > .x-button-drop-menu {
  display: block;
}

.x-button-drop-menu {
  position: fixed;
  width: 119px;
  top: 30px;
  /*right: 0;*/
  z-index: 1000;
  display: none;
  float: left;
  /*min-width: 160 px;*/
  padding: 5px 0;
  margin: 2px 0 0;
  font-size: 16px;
  list-style: none;
  background-color: #F2F2F2;
  background-clip: padding-box;
  border: 1px solid #E0E0E0;
  box-shadow: 1px 1px 2px #E0E0E0;
}

.x-button-drop-menu li a {
  display: block;
  padding: 3px 19px;
  clear: both;
  font-family: arial;
  color: #444;
  text-decoration: none;
  cursor: pointer;
  font-weight: bold;
}

.x-button-drop-menu li a:hover {
  background: #D3D3D3;
}

.status-button {
  font-size: 12px;
  border-style: inset;
}

.status-button img {
  width: 16px;
  height: 16px;
  vertical-align: middle;
  margin-right: 2px;
  top: 1px;
  position: relative;
}

.trans {
  background: transparent;
  color: #FFFFFF;
  border: none;
}

.open {
  background-color: greenyellow;
  /*border-width: 2px;*/
  border-color: greenyellow;
  border-style: inset;
  /*font-size: unset;*/
}

.connecting {
  background-color: yellow;
  border-width: 2px;
  border-color: greenyellow;
  border-style: inset;
}

.closing {
  background-color: gray;
  border-width: 2px;
  border-color: greenyellow;
  border-style: inset;
}

.closed {
  background-color: black;
  border-width: 2px;
  border-color: greenyellow;
  border-style: inset;
}
</style>