<template>
    <div style="overflow: hidden; display: flex; flex-direction: column">
        <dialog-editor-buttons class="dialog_editor_buttons" :cell="cell" @save="saveCode" @theme="changeTheme"/>
        <div class="f-full" style="overflow: hidden;">
            <editor v-model="code" @init="editorInit" :lang="lang" :theme="theme" width="100%" height="100%" style="display: flex; overflow: hidden;"></editor>
        </div>
    </div>
</template>

<script>
  //import MonacoEditor from 'vue-monaco'
  import DialogEditorButtons from './dialogEditorButtons.vue'
  import JavascriptTemplate from '!!raw-loader!./chartComponents/adaptorTemplate/javascript.txt';

  export default {
    name: "dialogEditor",
    components: {
      //MonacoEditor,
      editor: require('vue2-ace-editor'),
      DialogEditorButtons
    },
    data() {
      return {
        lang: 'javascript',
        theme: "monokai",
        code: ``,
        JavascriptTemplate: ""
      }
    },
    props: ['cell'],
    computed: {
      getCellId: function() {
        return this.cell.getId();
      }
    },
    methods: {
      editorInit: function() {
        require('ace-builds/src-noconflict/ext-language_tools');//language extension prerequsite...

        require('ace-builds/src-noconflict/mode-javascript');    //language

        require('ace-builds/src-noconflict/theme-monokai');
        require('ace-builds/src-noconflict/theme-chrome');
        require('ace-builds/src-noconflict/theme-ambiance');
        require('ace-builds/src-noconflict/theme-dracula');
        require('ace-builds/src-noconflict/theme-gruvbox');
        require('ace-builds/src-noconflict/theme-eclipse');
        require('ace-builds/src-noconflict/theme-solarized_light');
        //require('brace/snippets/javascript'); //snippet
        //require('brace/snippets/json'); //snippet
        //require('brace/snippets/xml'); //snippet
      },
      saveCode: function(evt) {
        let data = this.cell.getData();
        data.code = this.code;
        data.lang = evt.lang;
        data.theme = evt.theme;
        data.fName = evt.fName;
        //data = { ...data, ...evt };
        this.cell.setData(data);
      },
      changeTheme: function(evt) {
        this.theme = evt.theme;
        let nData = this.cell.getData();
        nData.theme = this.theme;
        this.cell.setData(nData);
      }
    },
    mounted() {
      this.JavascriptTemplate = JavascriptTemplate;
      if (this.cell.getData().theme && this.cell.getData().theme.length > 0)
        this.theme = this.cell.getData().theme;
      else
        this.theme = 'monokai';
      if (this.cell.getData().code && this.cell.getData().code.length > 0)
        this.code = this.cell.getData().code;
      else {
        this.code = this.JavascriptTemplate;
      }
    }
  }
</script>

<style scoped>
    .editor {
        width: 100%;
        height: 100%;
    }

    .f-full {
        width: 100%;
        height: 100%;
        flex-direction: column;
        display: flex;
        overflow: hidden;
    }
    .dialog_editor_buttons{
        display: flex;
        top: 0;
        position: inherit;
    }
    .panel-body.f-full.panel-body-noheader.panel-body-noborder{
        overflow: hidden;
    }

</style>