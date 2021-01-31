const type = "CHART";
import JavascriptTemplate from '!!raw-loader!./chartComponents/adaptorTemplate/javascript.txt';
function Config() {
  this.worker = 0;
  this.instances = 1;
  this.dataLimit = 100;
  this.chartType = 'line-chart';
  this.code = JavascriptTemplate;// JavascriptTemplate;
  this.enable = false;
  this.chartData = {
    "labels": [],
    "datasets": [
      {
        "label": "Count",
        "dataset": []
      }
    ]
  }
}

function Setting() {
  return {
    "Array": [
      1,
      2,
      3
    ],
    "Boolean": true,
    "Null": null,
    "Number": 123,
    "Object": { "a": "b", "c": "d" },
    "String": "Hello World"
  }
}

function JsonObject() {
  this.type = type.toLowerCase();
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;