<template>
    <div>
        <Tabs @tabSelect="tabSelect($event)" :scrollable="true" :headerHeight="64" style="height:250px">
            <TabPanel :title="'line'">
                <template slot="header">
                    <div class="tt-inner">
                        <img src="@/components/tools/mxChart/resources/images/line_chart.png">
                        <p>Line</p>
                    </div>
                </template>
                <p>
                    <button @click="setChartConfig('BasicLineChart')" class="button" style="vertical-align:middle">
                        <span>Basic Line Chart </span></button>
                    A line chart is a way of plotting data points on a line. Often, it is used to show trend data, or
                    the comparison of two data sets.<img
                        src="@/components/tools/mxChart/resources/images/new_window.png">
                </p>
            </TabPanel>
            <TabPanel :title="'bar'">
                <template slot="header">
                    <div class="tt-inner">
                        <img src="@/components/tools/mxChart/resources/images/bar_chart.png">
                        <p>Bar</p>
                    </div>
                </template>
                <p>
                    <button @click="setChartConfig('BasicBarChart')" class="button" style="vertical-align:middle">
                        <span>Basic Bar Chart </span></button>
                    A bar chart provides a way of showing data values represented as vertical bars. It is sometimes used
                    to show trend data, and the comparison of multiple data sets side by side.
                    <img src="@/components/tools/mxChart/resources/images/new_window.png">
                </p>
            </TabPanel>
            <TabPanel :title="'pie'">
                <template slot="header">
                    <div class="tt-inner">
                        <img src="@/components/tools/mxChart/resources/images/pie_chart.png">
                        <p>Pie</p>
                    </div>
                </template>
                <p>
                    <button @click="setChartConfig('BasicPieChart')" class="button" style="vertical-align:middle">
                        <span>Basic Pie Chart </span></button>
                    Pie chart is probably the most commonly used chart. It is divided into segments,
                    the arc of each segment shows the proportional value of each piece of data.

                    They are excellent at showing the relational proportions between data.
                    <img src="@/components/tools/mxChart/resources/images/new_window.png">
                </p>
            </TabPanel>
            <TabPanel :title="'tool'">
                <template slot="header">
                    <div class="tt-inner">
                        <img src="@/components/tools/mxChart/resources/images/tool_chart.png">
                        <p>Tool</p>
                    </div>
                </template>
                <p>A tablet computer, or simply tablet, is a one-piece mobile computer. Devices typically have a
                    touchscreen, with finger or stylus gestures replacing the conventional computer mouse.</p>
            </TabPanel>
            <TabPanel :title="'board'">
                <template slot="header">
                    <div class="tt-inner">
                        <img src="@/components/tools/mxChart/resources/images/board_chart.png">
                        <p>Board</p>
                    </div>
                </template>
                <p>A tablet computer, or simply tablet, is a one-piece mobile computer. Devices typically have a
                    touchscreen, with finger or stylus gestures replacing the conventional computer mouse.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
                <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital
                    information, and also demodulates such a carrier signal to decode the transmitted information.</p>
            </TabPanel>
        </Tabs>
    </div>
</template>

<script>
  import { EventBus } from '../../../event-bus.js';

  export default {
    name: "dialogConfig",
    components: {},
    props: ["messageHandler"],
    data() {
      return {
        cell: null,
        store: this.$store,
        kind: "line"
      }
    },
    methods: {
      setChartConfig(chartName) {
        this.messageHandler("SET_CHART_CONFIG", {kind:this.kind, chartName:chartName});
      },
      tabSelect(panel) {
        this.kind = panel.title;
      },

    },
    mounted() {
    },
    computed: {
      console: () => console,
      window: () => window,
    }
  }
</script>

<style scoped>
    .tt-inner {
        line-height: 12px;
        padding-top: 5px;
        width: 80px;
    }

    .tt-inner img {
        border: 0;
        width: 32px;
        height: 32px;
    }

    .tt-inner p {
        margin: 0;
        padding: 0;
        font-size: 14px;
    }

    .button {
        display: inline-block;
        border-radius: 4px;
        background: unset;
        background-color: #f4511e;
        border: none;
        color: #FFFFFF;
        text-align: center;
        font-size: 20px;
        padding: 5px;
        width: 200px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 5px;
    }

    .button span {
        cursor: pointer;
        display: inline-block;
        position: relative;
        transition: 0.5s;
    }

    .button span:after {
        content: '\00bb';
        position: absolute;
        opacity: 0;
        top: 0;
        right: -20px;
        transition: 0.5s;
    }

    .button:hover span {
        padding-right: 25px;
    }

    .button:hover span:after {
        opacity: 1;
        right: 0;
    }

    .panel-body p {
        padding: 5px;
        text-align: justify;
    }
</style>