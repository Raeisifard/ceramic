<template>
    <mx-button :editor="editor" action="undo" label="Undo"
               image="/src/images/undo.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxUndoButton",
    components: {
      mxButton
    },
    data: function() {
      return {
        editor: this.$store.getters.getEditor
      }
    }
  }
</script>

<style scoped>

</style>