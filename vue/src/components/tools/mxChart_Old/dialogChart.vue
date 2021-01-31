<template>
    <div style="display: contents;">
        <component :is="cmd.name" :cell="cell"/>
    </div>
</template>

<script>
  import config from './dialogConfig.vue';
  export default {
    name: "dialogChart",
    components: {
      config
    },
    props: [ 'cell', 'cmd' ],
    methods: {
      closeMe: function() {
        this.$store.getters.getDialog.close();
        this.$store.dispatch("setCell", null);
      }
    }
  }
</script>
