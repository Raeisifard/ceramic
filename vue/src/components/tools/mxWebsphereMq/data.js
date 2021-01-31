const type  = "WEBSPHEREMQ";

function Config() {
  this.mode = "reading";
  this.ip = "172.0.0.1";
  this.port = 1414;
  this.qm = "my.Queue.Manager";
  this.qName = "TestQueue";
  this.user = "";
  this.pass = "";
  this.channelName = "SYSTEM.DEF.SVRCONN";
  this.instance = 1;
  this.readAhead = false;
}

function Setting() {this.count= 1;}

function JsonObject() {
  this.type = type.toLowerCase();
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;