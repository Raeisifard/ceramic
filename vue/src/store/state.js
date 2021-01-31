export default {
  cmd: null,
  /* title: nll,*/
  version: "1.0.0",
  cell: null,
  dialog: null,
  splash: true,
  editor: {},
  eb: null,
  bc: null,
  connected: false,
  deployed: false,
  components: [],//All widgets must save their setting object in this array.
  graphId: null,
  graphName: null,
  graphStatus: null,//"deployed"
  graphState: null,//"ready"
  graphProfile: {},
  jsonGraph: null,
  vueComponents: {},
  vueObject: {}
}