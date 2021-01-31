<template>
    <mx-button :editor="editor" action="toggleOutline" label="Outline"
               image="/editors/images/outline.gif"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxOutlineButton",
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
