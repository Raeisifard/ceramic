<template>
    <!-- Creates a container for the toolbar -->
    <div id="toolbarContainer">
        <!-- <mx-import-button></mx-import-button>
         <mx-export-button></mx-export-button>-->
        <!--<mx-deploy-button></mx-deploy-button>-->
        <mx-action-status></mx-action-status>
        <mx-space></mx-space>
        <mx-group-button></mx-group-button>
        <mx-delete-button></mx-delete-button>
        <mx-space></mx-space>
        <mx-cut-button></mx-cut-button>
        <mx-copy-button></mx-copy-button>
        <mx-paste-button></mx-paste-button>
        <mx-space></mx-space>
        <mx-undo-button></mx-undo-button>
        <mx-redo-button></mx-redo-button>
        <mx-space></mx-space>
        <mx-show-button></mx-show-button>
        <mx-print-button></mx-print-button>
        <mx-space></mx-space>

        <mx-console-button></mx-console-button>
        <mx-outline-button></mx-outline-button>
<!--
        <mx-tasks-button></mx-tasks-button>
-->
    </div>

</template>

<script>
  import mxSpace from '@/components/buttons/mxComponents/mxSpace.vue';
  import mxGroupButton from '@/components/buttons/mxGroupButton.vue';
  import mxDeleteButton from '@/components/buttons/mxDeleteButton.vue';
  import mxCutButton from '@/components/buttons/mxCutButton.vue';
  import mxCopyButton from '@/components/buttons/mxCopyButton.vue';
  import mxPasteButton from '@/components/buttons/mxPasteButton.vue';
  import mxUndoButton from '@/components/buttons/mxUndoButton.vue';
  import mxRedoButton from '@/components/buttons/mxRedoButton.vue';
  import mxShowButton from '@/components/buttons/mxShowButton.vue';
  import mxPrintButton from '@/components/buttons/mxPrintButton.vue';
  import mxExportButton from '@/components/buttons/mxActionGroup/mxExport/mxExportButton.vue';
  import mxImportButton from '@/components/buttons/mxActionGroup/mxImport/mxImportButton.vue';
  import mxConsoleButton from '@/components/buttons/mxConsoleButton.vue';
  import mxOutlineButton from '@/components/buttons/mxOutlineButton.vue';
  import mxTasksButton from '@/components/buttons/mxTasksButton.vue';
  import mxActionStatus from "./buttons/mxActionGroup/mxActionStatus.vue";

  export default {
    name: 'Toolbar',
    components: {
      mxActionStatus,
      mxSpace,
      mxGroupButton,
      mxDeleteButton,
      mxCutButton,
      mxCopyButton,
      mxPasteButton,
      mxUndoButton,
      mxRedoButton,
      mxShowButton,
      mxPrintButton,
      mxExportButton,
      mxImportButton,
      mxConsoleButton,
      mxOutlineButton,
      mxTasksButton
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #toolbarContainer {
        box-sizing: initial;
        position: absolute;
        white-space: nowrap;
        overflow: hidden;
        top: 0px;
        left: 0px;
        max-height: 24px;
        height: 36px;
        right: 0px;
        padding: 6px;
        background-image: url('/src/images/toolbar_bg.gif');
    }
</style>
