<template>
    <mx-button :editor="editor" action="paste" label="Paste"
               image="/src/images/paste.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxPasteButton",
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