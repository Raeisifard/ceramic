<template>
    <div style="display: contents;">
        <component :is="getDialogName()" :cell="cell"/>
    </div>
</template>

<script>
  //import config from './dialogConfig.vue';
  import configSqlServer from './dialogConfigSqlserver.vue';
  import configArangoDb from './dialogConfigArangodb.vue';
  import configNitrite from './dialogConfigNitrite.vue';
  import editor from './dialogEditor.vue';
  export default {
    name: "dialogDatabase",
    components: {
      editor,
      //config,
      configSqlServer,
      configArangoDb,
      configNitrite
    },
    props: [ 'cell', 'cmd' ],
    methods: {
      getDialogName: function(){
        if (this.cmd.name !== 'editor'){
          return this.cmd.name + this.cell.getData().dbType;
        }else{
          return this.cmd.name;
        }
      },
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