<template>
    <mx-button :editor="editor" action="cut" label="Cut"
               image="/src/images/cut.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxCutButton",
    components: {
      mxButton
    },
    data: function() {
      return {
        editor: {}
      }
    },
    mounted() {
      this.editor = this.$store.getters.getEditor;
    }
  }
</script>

<style scoped>

</style>