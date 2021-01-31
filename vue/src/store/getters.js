import { CONNECTION_STATUS } from "./constants.js";

export default {
  getUid: state => (length) => {
    if (!length)
      length = 8;
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
  getVersion: state => {
    return state.version;
  },
  isSplash: state => {
    return state.splash;
  },
  getEditor: state => {
    return state.editor;
  },
  getGraph: state => {
    return state.editor.graph;
  },
  getModel: state => {
    return state.editor.graph.getModel();
  },
  getCmd: state => {
    return state.cmd;
  },
  /* getTitle: state => {
     return state.title;
   },*/
  getCell: state => {
    return state.cell;
  },
  getDialog: state => {
    return state.dialog;
  },
  getEb: state => {
    return state.eb;
  },
  getBc: state => {
    if (state.bc == null) {
      // Connection to a broadcast channel
      state.bc = new BroadcastChannel(state.graphId);
    }
    return state.bc;
  },
  getConnected: state => {
    return state.connected;
  },
  getDeployed: state => {
    return state.deployed;
  },
  getConnectionStatus: state => {
    return Object.keys(CONNECTION_STATUS)[ state.eb.state ];
  },
  getComponents: state => {
    return state.components;
  },
  getComponentById: (state) => (id) => {
    return state.components.find(component => component.id === id);
  },
  getComponentByName: (state) => (name) => {
    return state.components.find(component => component.name === name);
  },
  getComponentsByType: (state) => (type) => {
    return state.components.filter((component) => component.type === type);
  },
  getGraphId: state => {
    return state.graphId;
  },
  getGraphName: state => {
    return state.graphName;
  },
  getGraphStatus: state => {
    return state.graphStatus;
  },
  getGraphState: state => {
    return state.graphState;
  },
  getGraphProfile: state => {
    return state.graphProfile;
  },
  getJsonGraph: state => {
    return state.jsonGraph;
  },
  getUserList: state => {
    return {
      labels: [],
      datasets: [
        {
          label: '',
          backgroundColor: '',
          data: []
        }
      ]
      /*labels: ['Jan', 'Feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec'],
      datasets: [
        {
          label: 'Data One',
          backgroundColor: '#f87979',
          data: [40, 20, 23, 16, 30, 0, 17, 10, 3, 37, 37, 29]
        }
      ]*/
    }
  },
  getVueComponent: (state) => (cell) => {
    return state.vueComponents[ cell.getType() ];
  },
  getVueComponentByObject: (state) => (ctx, cell, name) => {
    let vueObj;
    if (name === undefined)
      if (cell.getType() === 'switch')
        vueObj = state.vueObject[ "vue_" + cell.getData().config[ "switchName" ] ];
      else if (cell.getType() === 'chart') {
        vueObj = state.vueObject[ "vue_" + cell.getData().config[ "chartName" ] ];
      } else
        vueObj = state.vueObject[ "vue_" + cell.getType() ];
    else
      vueObj = state.vueObject[ "vue_" + name ];
    const ComponentClass = Vue.extend(vueObj);
    let instance = new ComponentClass({
      propsData: {
        cell: cell,
        store: ctx.$store
      }
    });
    instance.$mount(); // pass nothing
    return instance;
  }
}