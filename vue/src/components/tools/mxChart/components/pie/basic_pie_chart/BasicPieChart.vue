<script>
  import { Pie } from "vue-chartjs";
  import { mapState } from "vuex";
  import { EventBus } from "../../../../../../event-bus";

  export default {
    extends: Pie,
    name: "basicPieChart",
    data: function() {
      return {
        chartData: {
          labels: [],
          datasets: [
          ]
        },
        options: this.cell.getData().setting.options,
        adaptor: () => {
        },
        code: this.cell.getData().setting.adaptor
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
      fillData01() {
        return {
          labels: [this.getRandomInt(), this.getRandomInt()],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f8ac79',
              data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(),
                this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
            }, {
              label: 'Data Two',
              backgroundColor: '#8ce368',
              data: [this.getRandomInt(), this.getRandomInt()]
            }
          ]
        }
      },
      fillData() {
        return {
          labels: [this.getRandomInt()],
          datasets: [
            {
              label: 'Data One',
              data: [this.getRandomInt(), this.getRandomInt()]
            }
          ]
        }
      },
      getRandomInt() {
        return Math.floor(Math.random() * ( 100 ))
      }
    },
    created() {
      this.$store = this.store;
    },
    computed: {
      ...mapState([
        'graphStatus', 'graphState', 'graphId'
      ]),
      /*code: function() {
        return this.cell.getData().setting.adaptor;
      },*/
      console: () => console,
      window: () => window,
    },
    mounted() {
      let that = this;
      /*let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      if (this.$store.getters.getGraphStatus === 'deployed')
        this.chartData = {
          labels: [],
          datasets: [
            {
              label: '',
              backgroundColor: '#f8ac79',
              data: []
            }
          ]
        }*/
      this.adaptor = new Function("chartData", "msg", "options", "ctx", this.code);
      EventBus.$on(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}.trigger`.toLowerCase(), msg => {
        if (that.adaptor(this.chartData, msg, this.cell.getData().setting.options, that) !== false) {
          that.renderChart(this.chartData, this.options, that);
        }
      });
      EventBus.$on(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}.input`.toLowerCase(), msg => {
        if (that.adaptor(this.chartData, msg, this.cell.getData().setting.options, that) !== false)
          that.$data._chart.update();
      });
      if (this.$store.getters.getGraphStatus === 'deployed')
        EventBus.$emit(`${this.graphId}.${this.cell.getType()}.${this.cell.getId()}.trigger`.toLowerCase(), { body: { signal: "ready" } });
      this.renderChart(this.chartData, this.options);
    },
    /*watch: {
      code(val) {
        this.adaptor = new Function("chartData", "msg", "options", "ctx", val);
        this.renderChart(this.chartData, this.options);
      }
    },*/
  }
</script>

<style scoped>

</style>