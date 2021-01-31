const type = "CHART";
import JavascriptTemplate from '!!raw-loader!./javascript.txt';

function Config() {
  this.kind = 'line';
  this.chartName = 'BasicLineChart';
  this.fillColor = 'rgba(150,241,255,0.65)';
  this.adaptorTheme = 'monokai';
}

function Setting() {
  this.dataLimit = 100;
  this.adaptor = JavascriptTemplate;
  this.options = {
    "responsive": true,
    "maintainAspectRatio": false,
    "scales": {
      "yAxes": [{
        "stacked": false
      }]
    },
    "legend": {
      "show": false
    }
  }
}

function JsonObject() {
  this.type = type.toLowerCase();
  this.config = new Config();
  this.setting = new Setting();
}

let chartData = {
  "labels": [],
  "datasets": [
    {
      "label": "Count",
      "dataset": []
    }
  ]
  /*labels: ['Jan', 'Feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec'],
    datasets: [
      {
        label: 'Data One',
        backgroundColor: '#f87979',
        data: [40, 20, 23, 16, 30, 0, 17, 10, 3, 37, 37, 29]
      }
    ]*/
}
export default JsonObject;