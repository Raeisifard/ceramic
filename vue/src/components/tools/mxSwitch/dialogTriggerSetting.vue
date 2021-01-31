<template>
    <div style="display: contents;">
        <div class="f-full" style=" margin: 0px 0px;display: block;">
            <div style="margin:5px 10px">
                <Label for="period_id" align="center">Period:</Label>
                <NumberBox inputId="period_id" v-model="period" suffix="ms" :increment="1000" :value="0" :min="0"
                           :max="99000000">
                    <Addon>
                        <span v-if="period!=null" class="textbox-icon icon-clear" @click="period=null"></span>
                    </Addon>
                </NumberBox>
                <Label for="delay_id" align="center" style="margin-left: 50px;">Delay:</Label>
                <NumberBox inputId="delay_id" v-model="delay" suffix="ms" :increment="100" :value="0" :min="0"
                           :max="3600000">
                    <Addon>
                        <span v-if="delay!=null" class="textbox-icon icon-clear" @click="delay=null"></span>
                    </Addon>
                </NumberBox>
            </div>
            <div ref="jsoneditor" style="height: 100%;padding-bottom: 40px;">

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
        name: "dialogTriggerSetting",
        components: {},
        data() {
            return {
                trigger: true,
                relay: false,
                push: false,
                period: 0,
                delay: 0,
                data: this.cell.getData(),
                options: {},
                editor: null
            }
        },
        props: ['cell'],
        //computed: {},
        methods: {
            configOk(evt) {
               /* let editor = this.$store.getters.getEditor;
                let graph = editor.graph;
                let model = graph.getModel();*/
                // get json
              let setting = this.editor.get();
              setting._ = this.data.setting._
              setting._.period = this.period;
              setting._.delay = this.delay;
              this.data.setting = setting;
                window.easyDialog.close();
            },
            configCancel(evt) {
                window.easyDialog.close();
            }
        },
        mounted() {
            this.period = this.data.setting._.period;
            this.delay = this.data.setting._.delay;
            this.options = {
                mode: 'tree',
                modes: ['tree', 'view', 'form', 'code', 'text']
            };
            this.editor = new JSONEditor(this.$refs.jsoneditor, this.options);
            let cloneSetting = Object.assign({}, this.data.setting);
            delete cloneSetting._
            this.editor.set(cloneSetting);
        }
    }
</script>

<style scoped>
</style>