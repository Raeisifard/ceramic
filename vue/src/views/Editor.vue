<template>
    <div>
        <!--<Layout style="width:700px;height:250px;">
            <LayoutPanel ref="west" style="width:180px"
                         region="west"
                         headerCls="side-menu-header"
                         bodyCls="f-column"
                         :float="true"
                         :border="false"
                         :collapsed="true">
                <template slot="header">
                    <svg class="side-toggle" viewBox="0 0 24 24" @click="$refs.west.collapse()"><path d="M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z"></path></svg>
                    <span>Side Menu</span>
                </template>
                <div class="side-menu f-full">
                    <div class="side-menu-item">
                        <div class="side-menu-icon">
                            <svg class="side-toggle" viewBox="0 0 24 24"><path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path></svg>
                        </div>
                        <div class="side-menu-text">Login</div>
                    </div>
                    <div class="side-menu-item">
                        <div class="side-menu-icon">
                            <svg class="side-toggle" viewBox="0 0 24 24"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"></path></svg>
                        </div>
                        <div class="side-menu-text">Mark</div>
                    </div>
                </div>
            </LayoutPanel>
            <LayoutPanel region="center" headerCls="main-header" style="height:100%">
                <template slot="header">
                    <svg class="side-toggle" viewBox="0 0 24 24" @click="$refs.west.expand()"><path d="M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z"></path></svg>
                    <span>Main Title</span>
                </template>
                <div style="margin-top:50px;text-align:center;font-size:16px">
                    <p>Click the top menu icon to expand the side panel.</p>
                </div>
            </LayoutPanel>
        </Layout>-->
        <splash v-if="isSplash"></splash>

        <toolbar></toolbar>

        <sidebar></sidebar>

        <graph></graph>

        <status></status>

        <hints></hints>
    </div>
</template>

<script>
  // @ is an alias to /src
  import Splash from '@/components/Splash.vue';
  import Toolbar from '@/components/Toolbar.vue';
  import Sidebar from '@/components/Sidebar.vue';
  import Graph from '@/components/Graph.vue';
  import Outline from '@/components/Outline.vue';
  import Status from '@/components/Status.vue';
  import Hints from '@/components/Hints.vue';

  export default {
    name: 'editor',
    components: {
      Splash,
      Toolbar,
      Sidebar,
      Graph,
      Outline,
      Status,
      Hints
    },
    computed: {
      isSplash() {
        return this.$store.getters.isSplash;
      }
    },
    created() {
        // Assigns some global constants for general behaviour, eg. minimum
        // size (in pixels) of the active region for triggering creation of
        // new connections, the portion (100%) of the cell area to be used
        // for triggering new connections, as well as some fading options for
        // windows and the rubberband selection.
        mxConstants.MIN_HOTSPOT_SIZE = 16;
        mxConstants.DEFAULT_HOTSPOT = 1;

        // Enables guides
        mxGraphHandler.prototype.guidesEnabled = true;

        // Alt disables guides
        mxGuide.prototype.isEnabledForEvent = function(evt) {
          return !mxEvent.isAltDown(evt);
        };

        // Enables snapping waypoints to terminals
        mxEdgeHandler.prototype.snapToTerminals = true;

        // Creates a wrapper editor with a graph inside the given container.
        // The editor is used to create certain functionality for the
        // graph, such as the rubberband selection, but most parts
        // of the UI are custom in this example.
        let editor = new mxEditor();
        //window.editor = editor;
        window.store = this.$store;
        editor.deploy = function(mxCell) {
          return false;
        };
        var graph = editor.graph;
        var model = graph.getModel();
        this.$store.commit("SET_EDITOR", editor);
        // Disable highlight of cells when dragging from toolbar
        graph.setDropEnabled(false);

        // Uses the port icon while connections are previewed
        graph.connectionHandler.getConnectImage = function(state) {
          return new mxImage(state.style[ mxConstants.STYLE_IMAGE ], 16, 16);
        };

        // Centers the port icon on the target port
        graph.connectionHandler.targetConnectImage = true;

        // Does not allow dangling edges
        graph.setAllowDanglingEdges(false);

        // Disable highlight of cells when dragging from toolbar
        graph.setDropEnabled(false);

        // Uses the port icon while connections are previewed
        graph.connectionHandler.getConnectImage = function(state) {
          return new mxImage(state.style[ mxConstants.STYLE_IMAGE ], 16, 16);
        };

        // Centers the port icon on the target port
        graph.connectionHandler.targetConnectImage = true;

        // Does not allow dangling edges
        graph.setAllowDanglingEdges(false);
    }
  }
</script>

<style scoped>
    .main-header {
        color: #222;
        line-height: 30px;
    }
    .side-toggle {
        position: relative;
        display: inline-block;
        width: 24px;
        height: 24px;
        top: 6px;
        left: 5px;
        margin-right: 10px;
        color: #000;
        fill: #000;
        cursor: pointer;
    }
    .side-menu-header .side-toggle {
        color: #fff;
        fill: #fff;
    }
    .side-menu-header {
        background: #444;
        color: #fff;
        line-height: 30px;
    }
    .side-menu {
        background: #444;
        color: #fff;
        margin: 0;
        padding: 10px 0;
    }
    .side-menu-item {
        padding: 5px 0;
    }
    .side-menu-item:hover {
        background: #666;
    }
    .side-menu .side-toggle {
        color: #fff;
        fill: #fff;
        top: 0;
        left: 8px;
        margin-right: 14px;
    }
    .side-menu-icon {
        display: inline-block;
        vertical-align: middle;
    }
    .side-menu-text {
        display: inline-block;
        vertical-align: middle;
    }
</style>
