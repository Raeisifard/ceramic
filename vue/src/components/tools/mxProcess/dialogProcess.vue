<template>
    <div style="display: contents;">
        <component :is="cmd.name" :cell="cell"/>
    </div>
</template>

<script>
  //import dialogToolbar from './dialogToolbar.vue';
  //import dialogContent from './dialogContent.vue';
  //import dialogButton from './dialogButton.vue';
  import editor from './dialogEditor.vue';
  import config from './dialogConfig.vue';
  import setting from './dialogSetting.vue';
  export default {
    name: "dialogProcess",
    components: {
      //dialogToolbar,
      //dialogContent,
      //dialogButton,
      editor,
      config,
      setting
    },
    props: [ 'cell', 'cmd' ],
    methods: {
      closeMe: function() {
        this.$store.getters.getDialog.close();
        this.$store.dispatch("setCell", null);
      }
    },
    computed: {
      getCell: function() {
        return Vue.cell;
      }
    },
    mounted() {
      //this.$refs.dl.open()
    }
  }
</script>

<style scoped>

</style>