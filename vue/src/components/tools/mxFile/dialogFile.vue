<template>
    <div style="display: contents;">
        <component :is="cmd.name" :cell="cell"/>
    </div>
</template>

<script>
  import config from './dialogConfig.vue';
  import setting from './dialogSetting.vue';
  export default {
    name: "dialogFile",
    components: {
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
  }
</script>

<style scoped>

</style>