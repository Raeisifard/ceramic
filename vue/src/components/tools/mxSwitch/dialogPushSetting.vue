<template>
    <div style="display: contents;">
        <div class="f-full" style=" margin: 0px 0px;display: block;">
            <div ref="jsoneditor" style="height: 100%;">

            </div>
        </div>
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="configCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
    import "jsoneditor/dist/jsoneditor.min.css";
    import JSONEditor from 'jsoneditor/dist/jsoneditor.min.js';

    export default {
        name: "dialogPushSetting",
        components: {},
        data() {
            return {
                data: this.cell.getData(),
                options: {},
                editor: null
            }
        },
        props: ['cell'],
        methods: {
            configOk(evt) {
                this.data.setting = this.editor.get();
                window.easyDialog.close();
            },
            configCancel(evt) {
                window.easyDialog.close();
            }
        },
        mounted() {
            this.options = {
                mode: 'tree',
                modes: ['tree', 'view', 'form', 'code', 'text']
            };
            this.editor = new JSONEditor(this.$refs.jsoneditor, this.options);
            this.editor.set(this.data.setting);
        }
    }
</script>

<style scoped>
</style>