<template>
    <mx-button :editor="editor" action="redo" label="Redo"
               image="/src/images/redo.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxRedoButton",
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