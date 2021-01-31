<template>
    <mx-button :editor="editor" action="groupOrUngroup" label="(Un)group"
               image="/src/images/group.png"></mx-button>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';

  export default {
    name: "mxGroupButton",
    components: {
      mxButton
    },
    data: function(){
      return {
        editor : {}
      }
    },
    mounted() {
      const editor = this.$store.getters.getEditor;
      this.editor = editor;
      editor.addAction('groupOrUngroup', function(editor, cell) {
        cell = cell || editor.graph.getSelectionCell();
        if (cell != null && editor.graph.isSwimlane(cell)) {
          editor.execute('ungroup', cell);
        }
        else {
          editor.execute('group');
        }
      });

    }
  }
</script>

<style scoped>

</style>