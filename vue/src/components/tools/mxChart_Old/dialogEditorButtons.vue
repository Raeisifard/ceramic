<template>
    <div class="dialog-toolbar">
        <LinkButton iconCls="icon-save" :plain="true" @click="save">Save</LinkButton>
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
        theme: "monokai",
        themes: this.getThemes()
      };
    },
    watch: {
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
      changeTheme: function() {
        mxLog.info("change theme: ", this.theme);
        this.$emit("theme", { "theme": this.theme });
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