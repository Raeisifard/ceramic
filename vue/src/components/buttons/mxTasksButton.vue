<template>
    <mx-button :editor="editor" action="toggleTasks" label="Tasks"
               image="/editors/images/tasks.gif"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxTasksButton",
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
