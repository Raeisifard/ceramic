<template>
    <!-- Creates a container for the toolboox -->
    <div id="sidebarContainer">

        <mx-process :sidebar="sidebar"></mx-process>
        <mx-switch :sidebar="sidebar"></mx-switch>
        <mx-chart :sidebar="sidebar"></mx-chart>
        <mx-file :sidebar="sidebar"></mx-file>
        <mx-database :sidebar="sidebar"></mx-database>
        <mx-websphere-mq :sidebar="sidebar"></mx-websphere-mq>
        <!--<mx-pattern :sidebar="sidebar"></mx-pattern>-->
        <mx-parquet :sidebar="sidebar"></mx-parquet>
        <mx-buffer :sidebar="sidebar"></mx-buffer>
        <mx-website :sidebar="sidebar"></mx-website>
        <!--<mx-keys :sidebar="sidebar"></mx-keys>
        <mx-new-mail :sidebar="sidebar"></mx-new-mail>
        <mx-server :sidebar="sidebar"></mx-server>-->
    </div>
</template>

<script>
  import mxProcess from './tools/mxProcess/mxProcess.vue';
  import mxSwitch from './tools/mxSwitch/mxSwitch.vue';
  import mxWebsite from './tools/mxWebsite/mxWebsite.vue';
  import mxKeys from './tools/mxKeys.vue';
  import MxNewMail from "./tools/mxNewMail";
  import MxServer from "./tools/mxServer";
  import MxBuffer from "./tools/mxBuffer/mxBuffer";
  import MxChart from "./tools/mxChart/mxChart";
  import MxWebsphereMq from "./tools/mxWebsphereMq/mxWebsphereMq";
  import MxFile from "./tools/mxFile/mxFile";
  import MxDatabase from "./tools/mxDatabase/mxDatabase";
  //import MxBlockly from "./tools/mxBlockly/mxBlockly";
  //import MxPattern from "./tools/mxPattern/mxPattern";
  import MxParquet from "./tools/mxParquet/mxParquet";

  export default {
    name: 'Sidebar',
    components: {
      MxChart,
      MxBuffer,
      MxWebsphereMq,
      MxFile,
      MxDatabase,
      //MxBlockly,
      //MxPattern,
      MxParquet,
      MxServer,
      MxNewMail,
      mxWebsite,
      mxProcess,
      mxSwitch,
      mxKeys
    },
    data() {
      return {
        sidebar: {}
      }
    },
    methods: {
      showSplash: function(show) {
        this.$store.dispatch("showSplash", show);
      },
    },
    mounted() {
      let that = this;
      that.sidebar = that.$el;
      that.$nextTick(function() {
        that.showSplash(false);
      })
    }
  }
</script>

<style scoped>
    #sidebarContainer {
        box-sizing: initial;
        position: absolute;
        overflow: hidden;
        top: 36px;
        left: 0;
        bottom: 36px;
        max-width: 52px;
        width: 56px;
        padding-top: 10px;
        padding-left: 4px;
        background-image: url('/src/images/sidebar_bg.gif');
    }
</style>
