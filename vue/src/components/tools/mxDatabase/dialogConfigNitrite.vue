<template>
    <div style="display: contents;overflow: hidden;">
        <div class="f-full">
            <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: space-between;">
                <Label for="ip" align="top" style="display: flex;">MQ ip:</Label>
                <TextBox inputId="ip" iconCls="icon-ip" placeholder="172.0.0.1" v-model="ip"
                         style="width:40%; display: flex;"></TextBox>
                <Label for="port" align="top" style="display: flex;">Port:</Label>
                <NumberBox inputId="port" placeholder=1414 v-model="port" :max=65536 :min=0 iconCls="icon-port"
                           iconAlign="left"
                           style="width:25%; display: flex;"></NumberBox>
            </div>

            <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: space-between;">
                <Label for="manager" align="top" style="display: flex;">MQ manager:</Label>
                <TextBox inputId="manager" v-model="qm" placeholder="my.Queue.Manager" iconCls="icon-qm"
                         style="width:30%; display: flex;"></TextBox>
                <Label for="queue" align="top" style="display: flex;">Queue:</Label>
                <TextBox inputId="queue" v-model="qName" placeholder="TestQueue" iconCls="icon-queue"
                         style="width:30%; display: flex;"></TextBox>
            </div>

            <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: space-between;">
                <Label for="user" align="top" style="display: flex;">Username:</Label>
                <TextBox inputId="user" v-model="user" placeholder="Username" iconCls="icon-man"
                         style="width:30%; display: flex;"></TextBox>
                <!--<TextBox inputId="user" v-model="user" placeholder="Username" iconCls="icon-user" style="width:30%; display: flex;"></TextBox>-->
                <Label for="pass" align="top" style="display: flex;">Password:</Label>
                <PasswordBox inputId="pass" v-model="pass" placeholder="Password"
                             style="width:30%; display: flex;"></PasswordBox>
                <!--<TextBox inputId="pass" type="password" v-model="pass" placeholder="Password" iconCls="icon-pass" style="width:30%; display: flex;"></TextBox>-->
            </div>

            <div style="margin-bottom:10px; display: flex;flex-direction: row;justify-content: space-between;">
                <Label for="channel" align="top" style="display: flex;">Channel:</Label>
                <TextBox inputId="queue" v-model="channelName" placeholder="SYSTEM.DEF.SVRCONN" iconCls="icon-channel"
                         style="width:40%; display: flex;"></TextBox>
                <Label for="instance" align="top" style="display: flex;">Instances:</Label>
                <NumberBox inputId="instance" :max=100 :min=1 v-model=instance iconCls="icon-instance" iconAlign="left"
                           style="width:20%; display: flex;"></NumberBox>
            </div>

            <div style="margin-bottom:0px; display: flex;flex-direction: row;justify-content: space-around;align-items: center; ">
                <div style="display: flex;border: ridge; padding: 5px; border-radius: 10px;">
                    <div class="fitem" style="display: flex; flex-direction: column;margin-right: 5px">
                        <Label for="s1">MQ Mode:</Label>
                        <SwitchButton inputId="s1" v-model="mode" onText="Reading" offText="Writing"
                                      style="width:100px"></SwitchButton>
                    </div>
                    <div style="display: flex;align-items: flex-start;flex-direction: column;border-left-style: dashed;padding-left: 5px;">
                        <div style="display: flex; flex-direction: row;">
                            <Label for="cp" style="margin-right: 2px;">Code page:</Label>
                            <TextBox :disabled="!mode" inputId="cp" v-model="codepage" placeholder="1098"
                                     iconCls="icon-codepage"
                                     style="width:70px; display: flex;"></TextBox>
                        </div>
                        <div v-if="mode" style="display: flex; flex-direction: row; align-items: center;">
                            <CheckBox :disabled="!mode" :multiple="false"
                                      v-model="readahead"></CheckBox>
                            <Label disabled="true" style="width: 90px; margin-left: 5px;"
                                   iconCls="icon-codepage">Read ahead</Label>
                        </div>
                        <div v-else style="display: flex; flex-direction: row; align-items: center;">
                            <CheckBox :multiple="false" v-model="buffer"></CheckBox>
                            <Label style="width: 90px; margin-left: 5px;" iconCls="icon-codepage">Buffer</Label>
                        </div>
                    </div>
                </div>
                <LinkButton :iconCls="testStatus" size="large" @click="testConnection" iconAlign="top"
                            style="display: flex;">Test Connection
                </LinkButton>
            </div>
        </div>
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
            <LinkButton style="width:80px" @click="configCancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  import { EventBus } from "../../../event-bus";

  export default {
    name: "dialogConfigNitrite",
    data() {
      return {
        //uuid: this.uuidv4(),
        cConfig: this.cell.getData().config,
        ip: null,
        port: null,
        qm: null,
        qName: null,
        user: null,
        pass: null,
        channelName: null,
        instance: 1,
        //mode: true,//true: 'Reading' & false: 'Writing'
        mode: !(this.cell.getData().config.mode === "writing"),
        codepage: this.cell.getData().config.codePage,
        readahead: false,
        testStatus: "unknown", //"ok", "failed
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
        data.config.port = this.port || 1414;
        data.config.qm = this.qm || "my.Queue.Manager";
        data.config.qName = this.qName || "TestQueue";
        data.config.user = this.user;
        data.config.pass = this.pass;
        data.config.channelName = this.channelName || "SYSTEM.DEF.SVRCONN";
        data.config.instance = this.instance;
        data.config.mode = this.mode ? "reading" : "writing";
        data.config.codePage = this.codepage || '1098';
        data.config.readAhead = this.readahead || false;
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
        config.port = this.port || 1414;
        config.qm = this.qm || "my.Queue.Manager";
        config.qName = this.qName || "TestQueue";
        config.user = this.user;
        config.pass = this.pass;
        config.channelName = this.channelName || "SYSTEM.DEF.SVRCONN";
        config.instance = this.instance;
        config.mode = this.mode ? "reading" : "writing";
        config.codePage = this.codepage || '1098';
        config.readAhead = this.readahead || false;
        this.eb = this.$store.getters.getEb;
        let headers = {
          "cmd": "TEST-CONNECTION",
          "id": this.cell.getId(),
          "type": this.cell.getType(),
          //"uuid": this.uuid,
          //"graph_id": this.$store.getters.getGraphId,
          //"graph_name": this.$store.getters.getGraphName
        };
        this.eb.send('mx.vx', config, headers, (err, res) => {
          if (err == null) {
            this.testStatus = JSON.parse(res.body).testStatus.toLowerCase()
          } else {
            mxLog.warn(JSON.stringify(err));
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
      if (data.config.qm)
        this.qm = data.config.qm;
      if (data.config.qName)
        this.qName = data.config.qName;
      if (data.config.user)
        this.user = data.config.user;
      if (data.config.pass)
        this.pass = data.config.pass;
      if (data.config.channelName)
        this.channelName = data.config.channelName;
      if (data.config.instance)
        this.instance = data.config.instance;
      if (typeof data.config.mode != "undefined")
        this.mode = !( data.config.mode === "writing" );
      if (data.config.codepage)
        this.codepage = data.config.codePage;
      if (data.config.readAhead)
        this.readahead = data.config.readAhead;
      this.subscribeTestConnection();
    }
  }
</script>
<style>
    .icon-ip {
        background-repeat: no-repeat;
        background-image: url('./src/icons/ip.png');
    }


    .icon-port {
        background-repeat: no-repeat;
        background-image: url('./src/icons/port.png');

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