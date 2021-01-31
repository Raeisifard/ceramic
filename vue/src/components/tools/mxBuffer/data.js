const type  = "BUFFER";

function Config() {
  this.autoNext = true;
  this.bufferEnable = true;
  this.bufferSize = 1000;
  this.routTrigger = false;
  this.timer = 5;
}

function Setting() {}

function JsonObject() {
  this.type = type.toLowerCase();
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;