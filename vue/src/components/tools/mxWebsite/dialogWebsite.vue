<template>
    <div style="display: contents;">
       <!-- <dialog-toolbar/>
        <dialog-content :cell="cell"/>-->
        <!--<dialog-button v-on:close="$store.commit('SET_RENDERMXWINDOW', false)"/>-->
       <!-- <dialog-button v-on:close_me="closeMe" :cell="cell"/>-->
        <dialog-editor :cell="cell"/>
    </div>
</template>

<script>
  //import dialogToolbar from './dialogToolbar.vue';
  //import dialogContent from './dialogContent.vue';
  //import dialogButton from './dialogButton.vue';
  import dialogEditor from './dialogEditor.vue';
  export default {
    name: "dialogWebsite",
    components: {
      //dialogToolbar,
      //dialogContent,
      //dialogButton,
      dialogEditor
    },
    props: [ 'cell' ],
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