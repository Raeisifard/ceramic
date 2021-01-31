<template>
    <Dialog ref="dl" @close="closeComponent()" style="left: unset"
            :title="cmd ? cmd.title : null || cell?cell.getType().toUpperCase()+' Config and Settings':''"
            :dialogStyle="{width:a, height:b, left: 'unset'}"
            dialogCls="mydlg"
            bodyCls="f-column"
            :modal="true"
            :draggable="true"
            :resizable="true"
            :closed="true"
            :showHeader="true">
        <component v-if="cell" :is="cellType" :cell="cell" :cmd="cmd" :reRender="reRender"/>
    </Dialog>
</template>
<script>
  import dialogWebsite from '../mxWebsite/dialogWebsite.vue';
  import dialogProcess from '../mxProcess/dialogProcess.vue';
  import dialogSwitch from '../mxSwitch/dialogSwitch.vue';
  import dialogBuffer from '../mxBuffer/dialogBuffer.vue';
  //import dialogChart from '../mxChart/dialogChart.vue';
  import dialogWebspheremq from '../mxWebsphereMq/dialogWebsphereMq.vue';
  import dialogFile from '../mxFile/dialogFile.vue';
  import dialogDatabase from '../mxDatabase/dialogDatabase.vue';

  export default {
    name: 'mxWindow',
    components: {
      dialogWebsite,
      dialogProcess,
      dialogSwitch,
      dialogBuffer,
      //dialogChart,
      dialogWebspheremq,
      dialogFile,
      dialogDatabase
    },
    data() {
      return {
        a: '90%',
        b: '80%',
        isCell: false,
        reRender: 0
      }
    },
    watch: {
      cmd: function (val) {
        if(val != null) {
          this.a = val.style.width;
          this.b = val.style.height;
        }
      }
    },
    computed: {
      /*getWidth: function(){
        if(this.cmd && this.cmd.style)
          return this.cmd.style.width;
      },
      getHeight: function(cmd){
        if(this.cmd && this.cmd.style)
          return this.cmd.style.height;
      },*/
      cellType: function() {
        return 'dialog-' + this.cell.getType().toLowerCase();
      }/*,
      muteCell: function() {
        if (this.cell)
          this.isCell = true;
      }*/
    },
    methods: {
      closeComponent: function() {
        this.reRender += 1;
      },
      getCmd: function() {
        return this.$store.getters.getCmd;
      }
    },
    props: [ 'cell', 'cmd' ],
    mounted() {
      //this.a = cmd.style.width;
      //this.b = cmd.style.height;
      this.$store.dispatch("setDialog", this.$refs.dl);
      window.easyDialog = this.$refs.dl;
      if (this.cell)
        this.isCell = true;
      this.$forceUpdate();
      this.$el.style.left = 'unset';
    }
  }
</script>

<style>
    .mydlg {
        left: unset;
        width: 400px;
        border-radius: 0;
        background: #fff;
    }
</style>
