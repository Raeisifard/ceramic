const type = "DATABASE";

function Config() {
this.ip = "";
this.port = 1433;
this.user = "";
this.pass = "";
this.dbName = "";
this.instance = 1;
this.buffer = 1;
}

function Setting() {
 this.count = 1;
 this.query = "";
}

function JsonObject() {
  this.type = type.toLowerCase();
  this.dbType = "SqlServer";
  this.theme = "sqlserver";
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;