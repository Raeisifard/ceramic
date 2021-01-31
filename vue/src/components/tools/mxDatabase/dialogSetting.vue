<template>
    <div style="display: contents;">
        <div class="f-full" style=" margin: 20px 20px;display: block;">
            <!-- <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
             <Label for="count" align="top" style="display: flex;">Count:</Label>
             <NumberBox inputId="count" @valueChange="change" :max=1000 :min=0 v-model="value" iconCls="icon-port"
                        iconAlign="left" style="width:25%; display: flex;"></NumberBox>
             <div style="padding:20px">-->
            <Slider style="width:100%;" :max="1000" :min="0" v-model="count" :showTip="true"
                    :rule="[0,'|',250,'|',500,'|',750,'|',1000]"></Slider>
            <!--  <p>Current value: {{value}}</p>
          </div>-->
        </div>
        <div class="dialog-button">
            <LinkButton style="width:80px" @click="send">Send</LinkButton>
            <LinkButton style="width:80px" @click="cancel">Cancel</LinkButton>
        </div>
    </div>
</template>

<script>
  export default {
    name: "dialogSetting",
    components: {},
    data() {
      return {
        count: 1,
        config: {},
        setting: this.cell.getData().setting,
        options: {},
      }
    },
    props: ['cell'],
    computed: {},
    methods: {
      save(evt) {
        this.cell.data.setting = { count: this.count };
      },
      send(evt) {
         this.save(evt);
         this.$store.dispatch("setData2JsonGraph", {data:this.cell.getData().setting,id:this.cell.getId(),type: this.cell.getType()});//, this.cell.type, this.cell.id);
         //window.easyDialog.close();
      },
      cancel(evt) {
        window.easyDialog.close();
      }
    },
    mounted() {
      let data = this.cell.getData();
      if (data.setting.count)
        this.count = data.setting.count;
    }
  }
</script>

<style>
    .dialog-button {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
    }
</style>