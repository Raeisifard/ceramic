<template>
    <mx-button :editor="editor" action="print" label="Print"
               image="/src/images/printer.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxPrintButton",
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