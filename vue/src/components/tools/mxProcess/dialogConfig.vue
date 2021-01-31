<template>
    <div style="display: contents;">
        <div class="dialog-toolbar">
            <!-- <span style="display: block; margin: 22px 1px 0px 10px">
                <p>Out</p>
             </span>-->
            <span>
                <!--<Label for="n1" align="top">Number:</Label>-->
                <NumberBox style="width:120px" inputId="n1" v-model="outNumber" :min="0" :max="22" :increment="1"
                           :spinners="true" suffix=" Outs" spinAlign="horizontal"></NumberBox>
            </span>
            <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
        </div>
        <div class="f-full" style=" margin: 0px 0px;display: block;">

            <div ref="jsoneditor" style="height: 100%;">
            </div>
        </div>

        <div class="dialog-button">
            <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="configCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  import "jsoneditor/dist/jsoneditor.min.css";
  import JSONEditor from 'jsoneditor/dist/jsoneditor.min.js';

  export default {
    name: "dialogConfig",
    components: {},
    data() {
      return {
        outNumber: this.getOutNumber(),
        value: 0,
        //json: {config: {}, setting: {}},
        options: {},
        editor: null
      }
    },
    props: ['cell'],
    computed: {},
    methods: {
      configOk(evt) {
        let editor = this.$store.getters.getEditor;
        let graph = editor.graph;
        let model = graph.getModel();
        if (( this.cell.getChildCount() - 4 ) !== this.outNumber) {
          model.beginUpdate();
          try {
            let existOut = this.cell.getChildCount() - 4;
            let extendOut = existOut - this.outNumber;
            let dist = Math.round(100 / ( this.outNumber + 3 )) / 100;
            let error = this.cell.getChildAt(2);
            let result = this.cell.getChildAt(3);
            let geo = null;
            let out = null;
            if (extendOut < 0) {//we need more outs
              for (let i = existOut; i < this.outNumber; i++) {
                let port = graph.insertVertex(this.cell, null, 'Out' + i, 1, dist + ( i + 1 ) * dist, 16, 16,
                  'port;image=editors/images/connector.gif;spacingLeft=18', true)
                port.geometry.offset = new mxPoint(-8, -8);
                port.direction = "out";
              }
              for (let i = 0; i < existOut; i++) {
                out = this.cell.getChildAt(i + 4);
                geo = out.getGeometry().clone();
                geo.y = dist + ( i + 1 ) * dist;
                graph.model.setGeometry(out, geo);
              }
            } else {//we need to remove excess outs
              let outs = [];
              for (let i = existOut + 3; i > this.outNumber + 3; i--) {
                outs.push(this.cell.getChildAt(i));
              }
              graph.removeCells(outs, true);
              for (let i = 4; i < this.cell.getChildCount(); i++) {
                out = this.cell.getChildAt(i);
                geo = out.getGeometry().clone();
                geo.y = dist + ( i - 3 ) * dist;
                graph.model.setGeometry(out, geo);
              }
            }
            geo = error.getGeometry().clone();
            geo.y = dist;
            graph.model.setGeometry(error, geo);
            geo = result.getGeometry().clone();
            geo.y = 1 - dist;
            graph.model.setGeometry(result, geo);
          } finally {
            model.endUpdate();
          }
        }
        this.cell.data.outNumber = this.outNumber;
        // get json
        this.cell.data.config = this.editor.get();
        window.easyDialog.close();
      },
      configCancel(evt) {
        window.easyDialog.close();
      },
      getOutNumber(){
        return this.cell.getData().outNumber
      }
    },
    mounted() {
      //this.outNumber = this.cell.getChildCount() - 4;
      //this.outNumber = this.cell.getData().outNumber;
      let data = this.cell.getData();
      //data.json = {...{config: {}, setting: {}}, ...data.json};
      /*this.config = {
        "worker": false,
        "instances": 1
      };
      this.config = {...this.config, ...data.config};*/
      this.options = {
        mode: 'tree',
        modes: ['tree', 'view', 'form', 'code', 'text']
      };
      this.editor = new JSONEditor(this.$refs.jsoneditor, this.options);
      this.editor.set(this.cell.data.config);
    }
  }
</script>

<style scoped>
</style>