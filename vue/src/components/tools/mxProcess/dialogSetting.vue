<template>
    <div style="display: contents;">
        <div class="dialog-toolbar">
            <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
        </div>
        <div class="f-full" style=" margin: 0px 0px;display: block;">

            <div ref="jsoneditor" style="height: 100%;">
            </div>
        </div>

        <div class="dialog-button">
            <LinkButton style="width:80px" @click="send">Send</LinkButton>
            <LinkButton style="width:80px" @click="save">Save</LinkButton>
            <LinkButton style="width:80px" @click="cancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  import "jsoneditor/dist/jsoneditor.min.css";
  import JSONEditor from 'jsoneditor/dist/jsoneditor.min.js';
  export default {
    name: "dialogSetting",
    components: {},
    data() {
      return {
        value: 0,
        config: {},
        setting: this.cell.getData().setting,
        options: {},
        editor: null
      }
    },
    props: [ 'cell' ],
    computed: {},
    methods: {
      save(evt){
        this.cell.data.setting = { ...this.editor.get() };
      },
      send(evt) {
        this.save();
        this.$store.dispatch("setData2JsonGraph", {data:this.cell.getData().setting,id:this.cell.getId(),type: this.cell.getType()});//, this.cell.type, this.cell.id);
        window.easyDialog.close();
      },
      cancel(evt) {
        window.easyDialog.close();
      }
    },
    mounted() {
      this.options = {
        mode: 'tree',
        modes: [ 'tree', 'view', 'form', 'code', 'text' ]
      };
      this.editor = new JSONEditor(this.$refs.jsoneditor, this.options);
      this.editor.set(this.setting);
    }
  }
</script>

<style scoped>
</style>