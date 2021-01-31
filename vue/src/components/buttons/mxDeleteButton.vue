<template>
    <mx-button :editor="editor" action="delete" label="Delete"
               image="/src/images/delete2.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxDeleteButton",
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