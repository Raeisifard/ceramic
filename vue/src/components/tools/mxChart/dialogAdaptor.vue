<template>
    <Panel :bodyStyle="{paddingRight:'3px'}" style="height:100%">
        <template slot="header">
            <div class="">
                <LinkButton class="" style="float: left;" iconCls="icon-save" :plain="true" @click='saveCode'>
                    Save
                </LinkButton>
                <div style="float: right;" class="theme">
                    <ComboBox v-model="theme" :data="themes" limitToList></ComboBox>
                </div>
            </div>
        </template>
        <editor v-model="value" @init="editorInit" :lang="lang" :theme="theme" :width="prop.width" :height="prop.height"></editor>
    </Panel>
</template>

<script>
  export default {
    name: "dialogAdaptor",
    components: {
      editor: require('vue2-ace-editor'),
    },
    data() {
      return {
        lang: 'javascript',
        theme: '',
        themes: this.getThemes(),
        value: this.prop.cell.getData().setting.adaptor,
      };
    },
    props: ['prop'],
    methods: {
      editorInit: function() {
        require('brace/ext/language_tools') //language extension prerequsite...
        require('brace/mode/html')
        require('brace/mode/javascript')    //language
        require('brace/theme/chrome')
        require('brace/theme/monokai')
        require('brace/theme/ambiance')
        require('brace/theme/dracula')
        require('brace/theme/gruvbox')
        require('brace/theme/eclipse')
        require('brace/theme/solarized_light')
      },
      settingOk(evt) {
        this.prop.wnd.destroy();
      },
      settingCancel(evt) {
        this.prop.wnd.destroy();
      },
      getThemes() {
        return [
          { value: "monokai", text: "Monokai" },
          { value: "chrome", text: "Chrome" },
          { value: "ambiance", text: "Ambiance" },
          { value: "dracula", text: "Dracula" },
          { value: "gruvbox", text: "Gruvbox" },
          { value: "solarized_light", text: "Solarized Light" },
          { value: "eclipse", text: "Eclipse" },
        ];
      },
      saveCode(evt) {
        this.prop.cell.getData().setting.adaptor = this.value;
      },
    },
    computed: {
      console: () => console,
      window: () => window,
    },
    mounted() {
      let that = this;
      this.theme = this.prop.cell.getData().config.adaptorTheme;
    },
    watch: {
      theme: function(newVal, oldVal) { // watch it
        this.prop.cell.getData().config.adaptorTheme = newVal;
      },
    }
  };
</script>
<style>
    .ace_scrollbar-v {
        bottom: 15px !important;
        width: 19px !important;
    }

    .theme {
        width: 14em;
    }
</style>