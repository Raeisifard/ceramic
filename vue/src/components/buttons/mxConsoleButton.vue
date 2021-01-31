<template>
    <mx-button :editor="editor" action="toggleConsole" label="Console"
               image="/editors/images/console.gif"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxConsoleButton",
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
