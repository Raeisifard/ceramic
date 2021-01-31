<template>
    <div style="display: contents">
        <dialog-editor-buttons :cell="cell" @save="saveCode" @lang="changeLang" @theme="changeTheme"/>
        <div class="f-full">
            <!-- <monaco-editor class="editor" v-model="code" :language="lang" :key="getRerender">
             </monaco-editor>-->
            <editor v-model="code" @init="editorInit" :lang="lang" :theme="theme" width="100%" height="100%"></editor>
        </div>
    </div>
</template>

<script>
  //import MonacoEditor from 'vue-monaco'
  import DialogEditorButtons from './dialogEditorButtons.vue'
  import JavaTemplate from '!!raw-loader!./langsTemplate/java.txt';
  import JavascriptTemplate from '!!raw-loader!./langsTemplate/javascript.txt';

  export default {
    name: "dialogEditor",
    components: {
      //MonacoEditor,
      editor: require('vue2-ace-editor'),
      DialogEditorButtons
    },
    data() {
      return {
        lang: 'java',
        theme: "monokai",
        code: ``,
        JavaTemplate: "",
        JavascriptTemplate: ""
      }
    },
    props: [ 'cell' ],
    computed: {
      getCellId: function() {
        return this.cell.getId();
      }
    },
    methods: {
      editorInit: function() {
        require('ace-builds/src-noconflict/ext-language_tools');//language extension prerequsite...
        require('ace-builds/src-noconflict/mode-html');
        require('ace-builds/src-noconflict/mode-javascript');    //language
        require('ace-builds/src-noconflict/mode-java');
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
      saveCode(evt) {
        let data = this.cell.getData();
        data.code = this.code;
        //data = { ...data, ...evt };
        this.cell.setData(data);
      },
      changeLang(evt) {
        this.lang = evt.lang;
        if (this.lang === 'java' && ( this.code === this.JavascriptTemplate || this.code.replace(/(\r\n|\n|\r)/gm, "").trim().length === 0 )){
          this.code = this.JavaTemplate;}
        else if (this.lang === 'javascript' && ( this.code === this.JavaTemplate || this.code.replace(/(\r\n|\n|\r)/gm, "").trim().length === 0 ))
          this.code = this.JavascriptTemplate;
      },
      changeTheme(evt) {
        this.theme = evt.theme;
        let nData = this.cell.getData();
        nData.theme = this.theme;
        this.cell.setData(nData);
      }
    },
    mounted() {
      this.JavaTemplate = JavaTemplate;
      this.JavascriptTemplate = JavascriptTemplate;
      if (this.cell.getData().lang && this.cell.getData().lang.length > 0)
        this.lang = this.cell.getData().lang;
      else
        this.lang = 'java';
      if (this.cell.getData().theme && this.cell.getData().theme.length > 0)
        this.theme = this.cell.getData().theme;
      else
        this.theme = 'monokai';
      if (this.cell.getData().code && this.cell.getData().code.length > 0)
        this.code = this.cell.getData().code;
      else {
        if (this.lang === "java")
          this.code = this.JavaTemplate;
        else if (this.lang === "javascript")
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
</style>