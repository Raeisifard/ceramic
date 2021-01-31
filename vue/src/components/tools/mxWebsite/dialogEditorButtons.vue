<template>
    <div class="dialog-toolbar">
        <LinkButton iconCls="icon-save" :plain="true" @click="save">Save</LinkButton>
        <div style="margin-left:10px;display:inline-block;" class="lang">
            <ComboBox inputId="c1" v-model="value" :data="data" limitToList></ComboBox>
        </div>
        <!--<input type="text" size="25" v-model="fileName" placeholder="File Name" />-->
        <div style="margin-left:10px;display:inline-block;margin-right:1em">
            <TextBox inputId="t1" v-model="fileName" iconCls="icon-file" placeholder="File name"></TextBox>
        </div>
        <div style="margin-right:10px;display:inline-block;" class="lang">
            <ComboBox inputId="h1" v-model="theme" :data="themes" limitToList></ComboBox>
        </div>
        <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
    </div>
</template>

<script>
  export default {
    name: "dialogEditorButtons",
    data() {
      return {
        value: "java",
        theme: "monokai",
        fileName: "SampleVerticle",
        data: this.getData(),
        themes: this.getThemes()
      };
    },
    watch: {
      value: function(val) {
        if (val != null && val.trim().length > 1)
          this.$emit("lang", { "lang": val });
      },
      theme: function(val) {
        if (val != null && val.trim().length > 1)
          this.$emit("theme", { "theme": val });
      }
    },
    props: [ 'cell' ],
    computed: {},
    methods: {
      save() {
        this.$emit('save', { "lang": this.value, "theme": this.theme, "fName": this.fileName });
      },
      changeValue: function() {
        this.$emit("lang", { "lang": this.value });
      },
      changeTheme: function() {
        mxLog.debug("change theme: ", this.theme);
        this.$emit("theme", { "theme": this.theme });
      },
      getData() {
        return [
          { value: "java", text: "Java" },
          { value: "javascript", text: "Javascript" }
        ];
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
      }
    },
    mounted() {
      if (typeof this.cell.getData().lang != 'undefined') {
        let lang = this.cell.getData().lang;
        if (lang === 'java')
          this.value = "java";
        else //if (lang === 'javascript' || lang === 'js')
          this.value = "javascript";
      } else {
        this.value = "java";//Default lang is "Java"
      }
      if (typeof this.cell.getData().fName != 'undefined' && this.cell.getData().fName.trim().length > 0) {
        this.fileName = this.cell.getData().fName;
      }
      if (typeof this.cell.getData().theme != 'undefined' && this.cell.getData().theme.trim().length > 0) {
        this.theme = this.cell.getData().theme.trim();
      }
    }
  }
</script>

<style>
    LinkButton.icon-java {
        background: url("/src/images/javascript16x16.png") no-repeat center center;
    }

    .lang .f-field {
        width: 7.1em;
    }
</style>