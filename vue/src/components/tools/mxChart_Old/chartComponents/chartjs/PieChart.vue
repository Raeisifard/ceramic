<script>
  import { Pie } from 'vue-chartjs'
  import { EventBus } from '../../../../../event-bus.js';
  import { mapState } from 'vuex';

  export default {
    extends: Pie,
    name: "PieChart",
    data: function() {
      return {
        chartData: {}, adaptor: () => {
        }
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
      },
      options: {
        type: Object,
        default: null
      }
    },
    created() {
      this.$store = this.store;
    },
    computed: {
      ...mapState([
        'graphStatus', 'graphState', 'graphId'
      ]),
      code: function(){
        return this.cell.getData().code;
      }
    },
    mounted() {
      //JSON.parse(JSON.stringify(constants.config)),
      //'setting': constants.setting
      if (typeof this.cell.getData().config === 'undefined')
        this.cell.getData().config = JSON.stringify({
          "worker": 0,
          "instances": 1,
          "dataLimit": 100,
          "chartData": {
            "labels": [],
            "datasets": [
              {
                "label": "Count",
                "dataset": []
              }
            ]
          }
        });
      //this.adaptor = this.cell.getData().code.parseFunction();
      this.adaptor = new Function("chartData", "msg", "config", this.code);
      this.store.subscribe((mutation, state) => {
        switch (mutation.type) {
          case 'SET_GRAPH_STATE':
            const graphState = state.graphState;
            // Do whatever makes sense now
            if (graphState === 'ready') {
              EventBus.$on(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}`.toLowerCase(), msg => {
                this.adaptor(this.chartData, msg, this.cell.getData().config);
                this.$data._chart.update();
              });
            }
            break;
        }
      });
      /*labels: ['Jan', 'Feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec'],
      datasets: [
        {
          label: 'Data One',
          backgroundColor: '#f87979',
          data: [40, 20, 23, 16, 30, 0, 17, 10, 3, 37, 37, 29]
        }
      ]*/
      this.chartData = JSON.parse(this.cell.getData().config).chartData || this.chartData;
      //this.$nextTick(function() {
      this.renderChart(this.chartData, this.options);
      //});
    },
    watch: {
      code(val) {
        this.adaptor = new Function("chartData", "msg", "config", val);
        this.renderChart(this.chartData, this.options);
      }
    }
  }
</script>
