export default {
  SET_VERSION: (state, version) => {
    state.version = version;
  },
  SHOW_SPLASH: (state, show) => {
    state.splash = show;
  },
  SET_EDITOR: (state, editor) => {
    state.editor = editor;
  },
  SET_CMD: (state, cmd) => {
    state.cmd = cmd;
  },
  /* SET_TITLE: (state, title) => {
     state.title = title;
   },*/
  SET_CELL: (state, cell) => {
    state.cell = cell;
  },
  SET_DIALOG: (state, dialog) => {
    state.dialog = dialog;
  },
  SET_EB: (state, eb) => {
    state.eb = eb;
  },
  SET_BC: (state, bc) => {
    state.bc = bc;
  },
  SET_CONNECTED: (state, connected) => {
    state.connected = connected;
  },
  SET_DEPLOYED: (state, deployed) => {
    state.deployed = deployed;
  },
  SET_COMPONENTS: (state, components) => {
    state.components = components;
  },
  ADD_COMPONENT: (state, component) => {
    state.components.push(component);
  },
  SET_GRAPH_ID: (state, graphId) => {
    state.graphId = graphId;
  },
  SET_GRAPH_NAME: (state, graphName) => {
    state.graphName = graphName;
  },
  SET_GRAPH_STATUS: (state, graphStatus) => {
    state.graphStatus = graphStatus;
  },
  SET_GRAPH_STATE: (state, graphState) => {
    state.graphState = graphState;
  },
  SET_GRAPH_PROFILE: (state, graphProfile) => {
    state.graphProfile = graphProfile;
  },
  SET_JSON_GRAPH: (state, jsonGraph) => {
    state.jsonGraph = jsonGraph;
  },
  ADD_VUE_COMPONENT: (state, vueComponent) => {
    state.vueComponents[vueComponent.$options.name] = vueComponent;
  },
  ADD_VUE_OBJECT: (state, vueObject) => {
    state.vueObject[vueObject.name] = vueObject;
  }
}