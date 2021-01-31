<template>
    <Panel :panelStyle="{height:'100%'}">
        <div ref="jsoneditor" class="json_editor">
        </div>
        <template slot="footer">
            <div class="dialog-button">
                <LinkButton style="width:80px" @click="ok">OK</LinkButton>
                <LinkButton style="width:80px" @click="cancel">Cancel</LinkButton>
            </div>
        </template>
    </Panel>
</template>

<script>
  import "jsoneditor/dist/jsoneditor.min.css";
  import JSONEditor from 'jsoneditor/dist/jsoneditor.min.js';
  export default {
    data() {
      return {
        value: 0,
        config: {},
        setting: this.prop.cell.getData().setting.options,
        options: {},
        editor: null
      }
    },
    props: ['prop'],
    methods: {
      ok(evt) {
        this.prop.cell.getData().setting.options = this.editor.get();
        this.prop.wnd.destroy();
      },
      cancel(evt) {
        this.prop.wnd.destroy();
      },
    },
    mounted() {
      this.options = {
        mode: 'tree',
        modes: [ 'tree', 'view', 'form', 'code', 'text' ]
      };
      this.editor = new JSONEditor(this.$refs.jsoneditor, this.options);
      this.editor.set(this.setting);
    }
  };
</script>
<style scoped>
.json_editor{
    width: 100%;
    height: 100%;
}
</style>