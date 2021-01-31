<template>
    <div style="display: contents; overflow: hidden;">
        <div class="f-full">
            <div style="margin-bottom:10px; display: flex; flex-direction: row; justify-content: space-between;">
                <div style="display: flex; width: 60%;">
                    <Label for="ip" align="top" style="display: flex;">IP:</Label>
                    <TextBox inputId="ip" iconCls="icon-ip" placeholder="172.0.0.1" v-model="ip"
                             style="width:150px; display: flex;margin-left: 10px;"></TextBox>
                </div>
                <div style="display: flex; width: 40%;">
                    <Label for="port" align="top" style="display: flex;">Port:</Label>
                    <NumberBox inputId="port" placeholder=1433 v-model="port" :max=65536 :min=0 iconCls="icon-port"
                               iconAlign="left"
                               style="width: 100px; display: flex; margin-left: 10px;"></NumberBox>
                </div>
            </div>
            <div style="margin-bottom: 10px; display: flex; flex-direction: row; justify-content: space-between;">
                <div style="display: flex; width: 60%;">
                    <Label for="database" align="top" style="display: flex;">DB Name:</Label>
                    <TextBox inputId="database" v-model="dbName" placeholder="my.Database" iconCls="icon-database"
                             style="width: 150px; display: flex; margin-left: 10px;"></TextBox>
                </div>

            </div>
            <div style="margin-bottom: 10px; display: flex; flex-direction: row; justify-content: space-between;">
                <div style="display: flex; width: 40%;">
                    <Label for="instance" align="top" style="display: flex;">Instance:</Label>
                    <NumberBox inputId="instance" :max=100 :min=1 v-model=instance iconCls="icon-instance"
                               iconAlign="left"
                               style="width: 95px; display: flex; margin-left: 10px;"></NumberBox>
                </div>
                <div style="display: flex; width: 40%;">
                    <Label for="buffer" align="top" style="display: flex;">Buffer:</Label>
                    <NumberBox inputId="buffer" :max=1000 :min=1 v-model=buffer iconCls="icon-buffer"
                               iconAlign="left"
                               style="width: 95px; display: flex; margin-left: 10px;"></NumberBox>
                </div>
            </div>
            <div style="margin-bottom: 10px; display: flex; flex-direction: row; justify-content: space-between;; align-items: center;">
                <div style="width: 60%; height: 100%; display: flex; flex-direction: column; align-content: center; justify-content: space-between;">
                    <div style="display: flex;">
                        <Label for="user" align="top" style="display: flex;">Username:</Label>
                        <TextBox inputId="user" v-model="user" placeholder="Username" iconCls="icon-man"
                                 style="width: 60%; display: flex; margin-left: 10px;"></TextBox>
                    </div>
                    <div style="display: flex;">
                        <Label for="pass" align="top" style="display: flex;">Password:</Label>
                        <PasswordBox inputId="pass" v-model="pass" placeholder="Password"
                                     style="width: 60%; display: flex; margin-left: 10px;"></PasswordBox>
                    </div>
                </div>
                <div style="width: 40%; display: flex;  justify-content: space-around;">
                    <LinkButton :iconCls="testStatus" size="large" @click="testConnection" iconAlign="top"
                                style="display: flex;">Test Connection
                    </LinkButton>
                </div>
            </div>
        </div>
        <div class="dialog-button">
            <LinkButton style="width: 80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width: 80px" @click="configCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  import { EventBus } from "../../../event-bus";

  export default {
    name: "dialogConfigNitrite",
    data() {
      return {
        cConfig: this.cell.getData().config,
        ip: null,
        port: null,
        dbName: null,
        user: null,
        pass: null,
        buffer: 1,
        instance: 1,
        testStatus: "unknown", //"ok", "failed"
        eb: null
      }
    },
    props: ['cell'],
    methods: {
      configOk(evt) {
        /*let editor = this.$store.getters.getEditor;
        let graph = editor.graph;
        let model = graph.getModel();*/
        let data = this.cell.getData();
        data.config = {};
        data.config.ip = this.ip || "172.0.0.1";
        data.config.port = this.port || 1433;
        data.config.dbName = this.dbName;
        data.config.user = this.user;
        data.config.pass = this.pass;
        data.config.buffer = this.buffer;
        data.config.instance = this.instance;
        this.cell.setData(data);
        window.easyDialog.close();
      },
      configCancel(evt) {
        window.easyDialog.close();
      },
      testConnection() {
        this.testStatus = "unknown";
        let config = {};
        config = {};
        config.ip = this.ip || "172.0.0.1";
        config.port = this.port || 1433;
        config.dbName = this.dbName;
        config.user = this.user;
        config.pass = this.pass;
        config.buffer = this.buffer;
        config.instance = this.instance;
        this.eb = this.$store.getters.getEb;
        let headers = {
          "cmd": "TEST-CONNECTION",
          "id": this.cell.getId(),
          "type": this.cell.getType(),
          "dbType": this.cell.getData().dbType
        };
        this.eb.send('mx.vx.test.sqlserver', config, headers, (err, res) => {
          if (err == null) {
            this.testStatus = JSON.parse(res.body).testStatus.toLowerCase();
          } else {
            mxLog.warn(JSON.stringify(err));
            this.testStatus = "failed";
          }
        });
      },
      uuidv4: function() {
        return ( [1e7] + -1e3 + -4e3 + -8e3 + -1e11 ).replace(/[018]/g, c =>
          ( c ^ crypto.getRandomValues(new Uint8Array(1))[ 0 ] & 15 >> c / 4 ).toString(16)
        )
      },
      subscribeTestConnection() {
        EventBus.$on(this.uuid, msg => {
          this.testStatus = msg.testStatus.toLowerCase()
        });
      }
    },
    computed: {},
    mounted() {
      let data = this.cell.getData();
      if (data.config.ip)
        this.ip = data.config.ip;
      if (data.config.port)
        this.port = data.config.port;
      if (data.config.dbName)
        this.dbName = data.config.dbName;
      if (data.config.user)
        this.user = data.config.user;
      if (data.config.pass)
        this.pass = data.config.pass;
      if (data.config.buffer)
        this.buffer = data.config.buffer;
      if (data.config.instance)
        this.instance = data.config.instance;
      this.subscribeTestConnection();
    }
  }
</script>
<style>
    .icon-ip {
        background-repeat: no-repeat;
        background-image: url('./src/icons/ip.png');
    }

    .icon-database {
        background-repeat: no-repeat;
        background-image: url('./src/icons/database-icon.png');
    }

    .icon-port {
        background-repeat: no-repeat;
        background-image: url('./src/icons/port.png');

    }

    .icon-instance {
        background-repeat: no-repeat;
        background-image: url('./src/icons/instance.png');
    }

    .icon-buffer {
        background-repeat: no-repeat;
        background-image: url('./src/icons/buffer.png');
    }

    .ok {
        background-repeat: no-repeat;
        background-image: url('./src/icons/connection1.png');
    }

    .unknown {
        background-repeat: no-repeat;
        background-image: url('./src/icons/connection0.png');
    }

    .failed {
        background-repeat: no-repeat;
        background-image: url('./src/icons/connection-1.png');
    }

</style>
<style scoped>
    * {
        user-select: none;
    }

    .f-full {
        flex-direction: column;
        display: flex;
        margin: 10px 10px 0 10px;
    }

    .dialog-button {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
    }
</style>