const type = "PROCESS";
const lang = "java";
const code = require("./langsTemplate/java.txt").default;
const fName = "SampleVerticle";
const theme = "monokai";

function Config() {
  this.worker = false;
  this.instances = 1;
}

function Setting() {
  this.Array = [1, 2, 3];
  this.Boolean = true;
  this.Null=null;
  this.Number=123;
  this.Object={"a":"b","c":"d"};
  this.String = "Hello World";
}

function JsonObject() {
  this.type = type.toLowerCase();
  this.lang = lang;
  this.code = code;
  this.fName = fName;
  this.theme = theme;
  this.outNumber = 0;
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;