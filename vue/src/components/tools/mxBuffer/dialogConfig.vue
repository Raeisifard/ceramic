<template>
    <div style="display: contents;">
        <!--<div class="dialog-toolbar">

        </div>-->
        <div class="f-full">
            <div style="">
                <span>Enable buffer: </span>
                <CheckBox inputId="enable" v-model="bufferEnable"></CheckBox>
            </div>
            <div class="form_border" style="position: relative;">
                <div class="over_layer">
                    <div style="flex-direction: row; display: flex; justify-content: space-between;">
                        <div class="buffer_div">
                            <span style="margin-top: 1em;">Buffer size: </span>
                            <div class="buffer_size">
                                <Label for="size" align="top" style="display: flex;">Message count</Label>
                                <NumberBox class="buffer_count" inputId="size" v-model="bufferSizeValue"
                                           :increment="increment"
                                           :min="100" :max="1000000"
                                           spinAlign="vertical" :inputStyle="{textAlign:'center'}"
                                           style="width:50px;height:80px"></NumberBox>
                            </div>
                        </div>
                        <div class="display: flex;">
                            <h5>Sending messages to port</h5>
                            <div style="margin-bottom:10px">
                                <div>
                                    <RadioButton name="group1" inputId="Automatic" value="Automatic"
                                                 v-model="mechanism"></RadioButton>
                                    <Label for="Automatic">Automatic</Label>
                                </div>
                                <div>
                                    <RadioButton name="group1" inputId="Manual" value="Manual"
                                                 v-model="mechanism"></RadioButton>
                                    <Label for="Manual">Manual</Label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%;">
                    <div class="route_trigger">
                        <CheckBox v-model="routeTrigger"></CheckBox>
                        <span style="margin-left: 10px;">Route trigger to "Error"</span></div>
                    <div class="send_buffer">
                        <span>Send buffer count on every
                            <NumberBox ref="timer" :increment="1" v-model="timer" :min="0" :max="300"
                                       style="width:60px;">
                        </NumberBox> second</span>
                    </div>
                </div>
                <div class="layer" v-if="!bufferEnable"></div>
            </div>
        </div>
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="configCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  export default {
    name: "dialogConfig",
    data() {
      return {
        cConfig: this.cell.getData().config,
        increment: 1000,
        mechanism: "Automatic",
        bufferEnable: true,
        bufferSizeValue: 1000,
        routeTrigger: false,
        timer: 5
      }
    },
    props: ['cell'],
    methods: {
      configOk(evt) {
        /*let editor = this.$store.getters.getEditor;
        let graph = editor.graph;
        let model = graph.getModel();*/
        let data = this.cell.getData();
        data.config = {
          autoNext: this.mechanism === "Automatic",
          bufferEnable: this.bufferEnable,
          bufferSize: this.bufferSizeValue,
          routeTrigger: this.routeTrigger,
          timer: this.timer
        };
        this.cell.setData(data);
        window.easyDialog.close();
      },
      configCancel(evt) {
        window.easyDialog.close();
      }
    },
    mounted() {
      this.mechanism = this.cConfig.autoNext ? "Automatic" : "Manual";
      this.bufferEnable = this.cConfig.bufferEnable;
      this.bufferSizeValue = this.cConfig.bufferSize;
      this.routeTrigger = this.cConfig.routeTrigger;
      this.timer = this.cConfig.timer;
      this.$refs.timer.$el.firstChild.maxLength = 3;
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
        margin: 10px;
    }

    .form_border {
        position: relative;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        border-style: ridge;
        padding: 10px;
        margin: 10px;
    }

    .buffer_size {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 10px
    }

    .buffer_div {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        padding: 0 0;
        max-width: min-content;
    }

    .buffer_count {
        display: flex;
        width: 80px;
        height: 80px;
        justify-content: center;
    }

    .route_trigger {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin: 10px 0;
    }

    .send_buffer {
    }

    .over_layer {
        display: flex;
        flex-direction: column;
    }

    .layer {
        opacity: 0.5;
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 10;
        background-color: black;
    }
</style>