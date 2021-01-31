<template>
    <div class="dialog-toolbar">
        <LinkButton iconCls="icon-save" :plain="true" @click="save">Save</LinkButton>
        <!--<div style="margin-left:10px;display:inline-block;" class="lang">
            <ComboBox inputId="c1" v-model="value" :data="data" limitToList></ComboBox>
        </div>-->
        <!--<input type="text" size="25" v-model="fileName" placeholder="File Name" />-->
        <!--<div style="margin-left:10px;display:inline-block;margin-right:1em">
            <TextBox inputId="t1" v-model="fileName" iconCls="icon-file" placeholder="File name"></TextBox>
        </div>-->
        <div style="float: right;margin-right:10px;display:inline-block;" class="lang">
            <h4 style="display: unset;">Theme: </h4>
            <ComboBox inputId="h1" v-model="theme" :data="themes" limitToList style="width:150px;"></ComboBox>
            <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
        </div>
    </div>
</template>

<script>
  export default {
    name: "dialogEditorButtons",
    data() {
      return {
        theme: "monokai",
        data: this.cell.getData(),
        themes: this.getThemes()
      };
    },
    watch: {
      theme: function(val) {
        if (val != null && val.trim().length > 1)
          this.$emit("theme", { "theme": val });
      }
    },
    props: ['cell'],
    computed: {},
    methods: {
      save() {
        this.$emit('save', { "lang": this.value, "theme": this.theme, "fName": this.fileName });
      },
      changeTheme: function() {
        mxLog.debug("change theme: ", this.theme);
        this.$emit("theme", { "theme": this.theme });
      },
      getThemes() {
        return [
          { value: "monokai", text: "Monokai" },
          { value: "sqlserver", text: "SQLServer" },
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
      mxLog.debug("mounted");
      if (typeof this.cell.getData().theme != 'undefined' && this.cell.getData().theme.trim().length > 0) {
        this.theme = this.cell.getData().theme.trim();
      }
    }
  }
</script>

<style>

</style>