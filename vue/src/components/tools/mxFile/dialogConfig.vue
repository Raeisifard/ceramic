<template>
    <div style="display: contents;overflow: hidden;">
        <div class="f-full">
            <div style="display: flex;flex-direction: row;justify-content: space-around;">
                <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: start;">
                    <Label for="filePath" align="top" style="display: flex;">Folder:</Label>
                    <TextBox inputId="filePath" iconCls="icon-folder" placeholder="your\file\path" v-model="filePath"
                             style="width:200px; display: flex;"></TextBox>
                </div>
                <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: start;">
                    <Label for="fileNameMask" align="top" style="display: flex;">File:</Label>
                    <TextBox inputId="fileNameMask" iconCls="icon-file" placeholder="your\file\name" v-model="fileNameMask"
                             style="width:140px; display: flex;"></TextBox>
                </div>
            </div>
            <div style="display: flex;flex-direction: row;justify-content: space-around;">
                <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: start;">
                    <Label for="fileNameDoneExt" align="top" style="display: flex;">Done extension:</Label>
                    <TextBox inputId="filePath" iconCls="icon-done" placeholder=".done" v-model="fileNameDoneExt"
                             style="width:80px; display: flex;"></TextBox>
                </div>
                <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: start;">
                    <Label for="fileNameLockExt" align="top" style="display: flex;">File lock extension:</Label>
                    <TextBox inputId="fileNameLockExt" iconCls="file-lock" placeholder=".$" v-model="fileNameLockExt"
                             style="width:60px; display: flex;"></TextBox>
                </div>
            </div>
        </div>
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="settingOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="settingCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  import { EventBus } from "../../../event-bus";

  export default {
    name: "dialogConfig",
    data() {
      return {
        cConfig: this.cell.getData().config,
        filePath: this.cell.getData().config.filePath,
        fileNameMask: this.cell.getData().config.fileNameMask,
        fileNameDoneExt: this.cell.getData().config.fileNameDoneExt,
        fileNameLockExt: this.cell.getData().config.fileNameLockExt,
        eb: null
      }
    },
    props: ['cell'],
    methods: {
      settingOk(evt) {
        /*let editor = this.$store.getters.getEditor;
        let graph = editor.graph;
        let model = graph.getModel();*/
        let data = this.cell.getData();
        data.setting = {};
        data.setting.filePath = this.filePath;
        data.setting.fileNameMask = this.fileNameMask;
        data.setting.fileNameDoneExt = this.fileNameDoneExt;
        data.setting.fileNameLockExt = this.fileNameLockExt;
        this.cell.setData(data);
        window.easyDialog.close();
      },
      settingCancel(evt) {
        window.easyDialog.close();
      },
      uuidv4: function() {
        return ( [1e7] + -1e3 + -4e3 + -8e3 + -1e11 ).replace(/[018]/g, c =>
          ( c ^ crypto.getRandomValues(new Uint8Array(1))[ 0 ] & 15 >> c / 4 ).toString(16)
        )
      }
    },
    computed: {},
    mounted() {
      /*let data = this.cell.getData();
      if (data.setting.filePath)
        this.filePath = data.setting.filePath;
      if (data.setting.fileNameMask)
        this.fileNameMask = data.setting.fileNameMask;
      if (data.setting.fileNameDoneExt)
        this.fileNameDoneExt = data.setting.fileNameDoneExt;
      if (data.setting.fileNameLockExt)
        this.fileNameLockExt = data.setting.fileNameLockExt;*/
    }
  }
</script>

<style scoped>
    * {
        user-select: none;
    }

    .f-full {
        flex-direction: column;
        display: flex;
        margin: 10px 10px 0 10px;
    }

    .dialog-button {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
    }

    .icon-folder {
        background-repeat: no-repeat;
        background-image: url('./src/icons/folder-icon.png');
    }

    .icon-file {
        background-repeat: no-repeat;
        background-image: url('./src/icons/file-icon.png');
    }

    .file-lock {
        background-repeat: no-repeat;
        background-image: url('./src/icons/lock-icon.png');

    }

    .icon-done {
        background-repeat: no-repeat;
        background-image: url('./src/icons/done-icon.png');
    }
</style>