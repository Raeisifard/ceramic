<template>
    <div style="display: contents">
        <dialog-editor-buttons :cell="cell" @save="saveCode"  @theme="changeTheme"/>
        <div class="f-full">
            <!-- <monaco-editor class="editor" v-model="code" :language="lang" :key="getRerender">
             </monaco-editor>-->
            <editor v-model="query" @init="editorInit" :lang="lang" :theme="theme" width="100%" height="100%"></editor>
        </div>
    </div>
</template>

<script>
  //import MonacoEditor from 'vue-monaco'
  import DialogEditorButtons from './dialogEditorButtons.vue'
  import sqlservertips from '!!raw-loader!./src/queryTips/sqlserver.txt';
  import aqltips from '!!raw-loader!./src/queryTips/aql.txt';
  import nitritetips from '!!raw-loader!./src/queryTips/nitrite.txt';

  export default {
    name: "dialogEditor",
    components: {
      //MonacoEditor,
      editor: require('vue2-ace-editor'),
      DialogEditorButtons
    },
    data() {
      return {
        query: this.getQuery(),
        lang: this.getLang(),
        theme: this.cell.getData().theme,
        //queryTip: "",
        //JavascriptTemplate: ""
      }
    },
    props: [ 'cell' ],
    computed: {
      getCellId: function() {
        return this.cell.getId();
      }
    },
    methods: {
      getLang: function(){
        let lang = "sqlserver";
        switch (this.cell.getData().dbType){
          case "arangodb":
            lang = "aql";
            break;
          case "nitrite":
            lang = "java";
            break;
          case "sqlserver":
          default:
            lang = "sqlserver";
            break;
        }
        return lang;
      },
      getQuery: function(){
        if (this.cell.getData().setting.query.trim().length > 0)
          return this.cell.getData().setting.query
        else {
          let code = "sqlservertips";
          switch (this.cell.getData().dbType) {
            case "arangodb":
              code = aqltips;
              break;
            case "nitrite":
              code = nitritetips;
              break;
            case "sqlserver":
            default:
              code = sqlservertips;
              break;
          }
          return code;
        }
      },
      editorInit: function() {
        /*require('ace-builds/src-noconflict/ext-language_tools');//language extension prerequsite...
        require('ace-builds/src-noconflict/mode-aql');
        require('ace-builds/src-noconflict/mode-java');    //language
        require('ace-builds/src-noconflict/mode-sqlserver');
        require('ace-builds/src-noconflict/theme-monokai');
        require('ace-builds/src-noconflict/theme-chrome');
        require('ace-builds/src-noconflict/theme-ambiance');
        require('ace-builds/src-noconflict/theme-dracula');
        require('ace-builds/src-noconflict/theme-gruvbox');
        require('ace-builds/src-noconflict/theme-eclipse');
        require('ace-builds/src-noconflict/theme-sqlserver');
        require('ace-builds/src-noconflict/theme-solarized_light');*/

        require('brace/ext/language_tools'); //language extension prerequsite...
        require('brace/mode/aql');
        require('brace/mode/java');
        require('brace/mode/sqlserver');
        require('brace/theme/chrome');
        require('brace/theme/monokai');
        require('brace/theme/ambiance');
        require('brace/theme/dracula');
        require('brace/theme/gruvbox');
        require('brace/theme/eclipse');
        require('brace/theme/sqlserver');
        require('brace/theme/solarized_light');
      },
      saveCode(evt) {
        let data = this.cell.getData();
        data.setting.query = this.query;
        data.dbType = evt.dbType;
        data.theme = evt.theme;
        //data = { ...data, ...evt };
        this.cell.setData(data);
      },
      changeTheme(evt) {
        this.theme = evt.theme;
        let nData = this.cell.getData();
        nData.theme = this.theme;
        this.cell.setData(nData);
      }
    },
    mounted() {
      //this.JavaTemplate = JavaTemplate;
      //this.JavascriptTemplate = sqlservertips;
     /* if (this.cell.getData().lang && this.cell.getData().lang.length > 0)
        this.lang = this.cell.getData().lang;
      else
        this.lang = 'sqlserver';*/
      /*if (this.cell.getData().theme && this.cell.getData().theme.length > 0)
        this.theme = this.cell.getData().theme;
      else
        this.theme = 'monokai';
      if (this.cell.getData().code && this.cell.getData().code.length > 0)
        this.code = this.cell.getData().code;*/
     /* else {
        this.code = sqlservertips;
      }*/
    }
  }
</script>

<style scoped>
    .editor {
        width: 100%;
        height: 100%;
    }
</style>