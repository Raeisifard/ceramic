<template>
    <mx-button :editor="editor" action="copy" label="Copy"
               image="/src/images/copy.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxCopyButton",
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