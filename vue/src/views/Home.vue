<template>
    <div>
        <!-- Creates a container for the splash screen -->
        <div id="splash"
             style="position:absolute;top:0px;left:0px;width:100%;height:100%;background:white;z-index:1;">
            <center id="splash1" style="padding-top:230px;">
                <img src="editors/images/loading.gif">
            </center>
        </div>

        <!-- Creates a container for the sidebar -->
        <div id="toolbarContainer"
             style="position:absolute;white-space:nowrap;overflow:hidden;top:0px;left:0px;max-height:24px;height:36px;right:0px;padding:6px;background-image:url('/src/images/toolbar_bg.gif');">
        </div>

        <!-- Creates a container for the toolboox -->
        <div id="sidebarContainer"
             style="position:absolute;overflow:hidden;top:36px;left:0px;bottom:36px;max-width:52px;width:56px;padding-top:10px;padding-left:4px;background-image:url('/src/images/sidebar_bg.gif');">
        </div>

        <!-- Creates a container for the graph -->
        <div id="graphContainer"
             style="position:absolute;overflow:hidden;top:36px;left:60px;bottom:36px;right:0px;background-image:url('editors/images/grid.gif');cursor:default;">
        </div>

        <!-- Creates a container for the outline -->
        <div id="outlineContainer"
             style="position:absolute;overflow:hidden;top:36px;right:0px;width:200px;height:140px;background:transparent;border-style:solid;border-color:black;">
        </div>

        <!-- Creates a container for the sidebar -->
        <div id="statusContainer"
             style="text-align:right;position:absolute;overflow:hidden;bottom:0px;left:0px;max-height:24px;height:36px;right:0px;color:white;padding:6px;background-image:url('/src/images/toolbar_bg.gif');">
            <div style="font-size:10pt;float:left;">
                Created with <a href="http://www.jgraph.com" target="_blank">mxGraph</a>
            </div>
        </div>
    </div>
</template>

<script>
  // @ is an alias to /src
  import HelloWorld from '@/components/HelloWorld.vue'

  export default {
    name: 'home',
    components: {
      HelloWorld
    }
  }
</script>
