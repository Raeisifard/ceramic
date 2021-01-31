<template>
    <div>
        <component :is="getChartType" :styles="cStyle" :store="store" :options="options" :cell="cell"></component>
    </div>
</template>
<script>
  import LineChart from './chartjs/LineChart';
  import PieChart from './chartjs/PieChart';
  import BarChart from './chartjs/BarChart';
  //import { mapState } from 'vuex';


  export default {
    name: "chart",
    components: {
      LineChart,
      PieChart,
      BarChart
    },
    data: function() {
      return {
        options: {
          responsive: true,
          maintainAspectRatio: false
        },
        chartData: []
      }
    },
    props: {
      cell: {
        type: Object,
        default: null
      },
      store: {
        type: Object,
        default: null
      }
    },
    methods: {
    },
    computed: {
     /* ...mapState([
        'graphStatus', 'graphState', 'graphId'
      ]),*/
      getChartType: function() {
        return this.cell.getData().chartType;
      },
      cStyle() {
        return {
          height: `${this.cell.geometry.height}px`,
          width: `${this.cell.geometry.width}px`,
          position: 'relative'
        }
      },
      code() {
        return this.cell.getData().code;
      }
    },
    watch: {
      getChartType(val) {
        this.renderChart(this.chartData, this.options);
      }
    }
  }
</script>
