<template>
    <div style="display: contents;">
        <!-- <div class="form_border" style="position: relative;">-->
        <Tabs style="height:100%">
            <TabPanel :title="'Chart'" class="norm">
                <div style="display: flex; flex-direction: column; align-items: center; justify-content: flex-start; overflow: hidden;">
                    <div style="padding: 10px 0; width: 100%; display: flex; flex-direction: row; align-items: center; justify-content: space-around">
                        <div style="display: flex; align-items: center; ">
                            <RadioButton name="group1" inputId="lineChart" value="line-chart"
                                         v-model="chartType"></RadioButton>
                        </div>
                        <h2 style="display: flex; ">Line Chart</h2>
                        <div style="display: flex; align-items: center; justify-content: flex-start; border-style: dashed; border-radius: 10px; background-color: brown;">
                            <img width="100px" height="70px" src="/src/images/LineChart.png">
                        </div>
                        <LinkButton iconCls="icon-edit">Edit</LinkButton>
                    </div>
                    <hr style="width: 90%;">
                    <div style="padding: 10px 0; width: 100%; display: flex; flex-direction: row; align-items: center; justify-content: space-around">
                        <div style="display: flex; align-items: center; ">
                            <RadioButton name="group1" inputId="BarChart" value="bar-chart"
                                         v-model="chartType"></RadioButton>
                        </div>
                        <h2 style="display: flex; ">Bar Chart</h2>
                        <div style="display: flex; align-items: center; justify-content: flex-start; border-style: dashed; border-radius: 10px; background-color: brown;">
                            <img width="100px" height="70px" src="/src/images/BarChart.png">
                        </div>
                        <LinkButton iconCls="icon-edit">Edit</LinkButton>
                    </div>
                    <hr style="width: 90%;">
                    <div style="padding: 10px 0; width: 100%; display: flex; flex-direction: row; align-items: center; justify-content: space-around">
                        <div style="display: flex; align-items: center; ">
                            <RadioButton name="group1" inputId="lineChart" value="pie-chart"
                                         v-model="chartType"></RadioButton>
                        </div>
                        <h2 style="display: flex; ">Pie Chart</h2>
                        <div style="display: flex; align-items: center; justify-content: flex-start; border-style: dashed; border-radius: 10px; background-color: brown;">
                            <img width="100px" height="70px" src="/src/images/PieChart.png">
                        </div>
                        <LinkButton iconCls="icon-edit">Edit</LinkButton>
                    </div>
                </div>
            </TabPanel>
            <TabPanel :title="'Config'" class="norm">
                <span ref="jsoneditor">
                </span>
            </TabPanel>
            <TabPanel :title="'Adaptor'" class="norm" style="overflow: hidden;">
                <!--<div ref="jseditor" class="f-full" style="height: 100%; min-height: 250%;">-->
                <jseditor :cell="cell" class="f-full" style="overflow: hidden;"></jseditor>
                <!--</div>-->
            </TabPanel>
            <TabPanel :title="'Help'" :closable="true" iconCls="icon-help" class="norm">
                <p>This is the help content.</p>
            </TabPanel>
        </Tabs>
        <!--</div>-->
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="configCancel">Cancel</LinkButton>
        </div>

    </div>
</template>

<script>
  import "jsoneditor/dist/jsoneditor.min.css";
  import JSONEditor from 'jsoneditor/dist/jsoneditor.min.js';
  import jseditor from './dialogEditor';
  //import * as constants from "./data.js";

  export default {
    name: "dialogConfig",
    components: { jseditor },
    data() {
      return {
        chartType: this.cell.getData().chartType,
        //constants: constants.default
      }
    },
    props: ['cell'],
    methods: {
      configOk(evt) {
        let data = this.cell.getData();
        data.config = JSON.stringify(this.editor.get());
        data.chartType = this.chartType;
        this.cell.setData(data);
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
      this.editor.set(this.cell.getData().config);
    }
  }
</script>

<style scoped>
    * {
        user-select: none;
    }

    .f-full {
        width: 100%;
        /*height: 100%;*/
        flex-direction: column;
        display: flex;
        overflow: hidden;
    }

    .form_border {
        height: 100%;
        position: relative;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
    }

    .norm {
        overflow: hidden;
        height: 336px;
    }
</style>