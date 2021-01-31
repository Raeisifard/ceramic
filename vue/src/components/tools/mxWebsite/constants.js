const type  = "WEBSITE";
function Config() {
  this.ip = "172.0.0.1";
  this.port = 1414;
  this.username = "my username";
  this.password = "my password";
  this.count = 100;
}
function Setting() {this.rate= 1;}
function JsonObject() {
  this.type = type.toLowerCase();
  this.config = new Config();
  this.setting = new Setting();
}
export default JsonObject;