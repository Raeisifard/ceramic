<template>
    <div class="f-full">
        <monaco-editor class="editor" v-model="code" language="javascript">
        </monaco-editor>
    </div>
</template>

<script>
  import MonacoEditor from 'vue-monaco'

  export default {
    name: "dialogContent",
    components: {
      MonacoEditor
    },
    data() {
      return {
        code: 'const noop = () => {}'
      }
    },
    props: [ 'cell' ],
    computed: {
      getCellId: function() {
        return this.cell.getId();
      }
    }
  }
</script>

<style scoped>
    .editor {
        width: 100%;
        height: 100%;
    }
</style>