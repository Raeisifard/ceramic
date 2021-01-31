<template>
    <!-- Creates a container for the sidebar -->
    <div id="statusContainer">
        <div style="height: 100%;">
            <img src="/editors/images/ceramic.png" style="height: 100%;">
        </div>
        <div style="height: 100%;">
            <img src="/editors/images/ceramic-icon.png" style="height: 100%;">
        </div>
        <div style="height: 100%;">
            ver. {{version}}
        </div>
    </div>
</template>

<script>
  export default {
    name: 'Status',
    props: {
      //msg: String
    },
    data: function() {
      return {
        //version: this.$store.getters.getVersion
      }
    },
    methods: {
      addToolbarButton: function(editor, toolbar, action, label, image, isTransparent) {
        var button = document.createElement('button');
        button.style.fontSize = '10px';
        if (image != null) {
          var img = document.createElement('img');
          img.setAttribute('src', image);
          img.style.width = '16px';
          img.style.height = '16px';
          img.style.verticalAlign = 'middle';
          img.style.marginRight = '2px';
          button.appendChild(img);
        }
        if (isTransparent) {
          button.style.background = 'transparent';
          button.style.color = '#FFFFFF';
          button.style.border = 'none';
        }
        mxEvent.addListener(button, 'click', function(evt) {
          editor.execute(action);
        });
        mxUtils.write(button, label);
        toolbar.appendChild(button);
      },
    },
    computed: {
      version: function () {
        return this.$store.getters.getVersion
      }
    },
    mounted(){
      let editor = this.$store.getters.getEditor;
      let status = this.$el;
      let spacer = document.createElement('div');
      spacer.style.display = 'inline';
      spacer.style.padding = '8px';
      // Adds toolbar buttons into the status bar at the bottom
      // of the window.
      this.addToolbarButton(editor, status, 'collapseAll', 'Collapse All', '/src/images/navigate_minus.png', true);
      this.addToolbarButton(editor, status, 'expandAll', 'Expand All', '/src/images/navigate_plus.png', true);

      status.appendChild(spacer.cloneNode(true));

      this.addToolbarButton(editor, status, 'enterGroup', 'Enter', '/src/images/view_next.png', true);
      this.addToolbarButton(editor, status, 'exitGroup', 'Exit', '/src/images/view_previous.png', true);

      status.appendChild(spacer.cloneNode(true));

      this.addToolbarButton(editor, status, 'zoomIn', '', '/src/images/zoom_in.png', true);
      this.addToolbarButton(editor, status, 'zoomOut', '', '/src/images/zoom_out.png', true);
      this.addToolbarButton(editor, status, 'actualSize', '', '/src/images/view_1_1.png', true);
      this.addToolbarButton(editor, status, 'fit', '', '/src/images/fit_to_size.png', true);
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #statusContainer {
        box-sizing: initial;
        text-align: right;
        position: absolute;
        overflow: hidden;
        bottom: 0px;
        left: 0px;
        max-height: 24px;
        height: 36px;
        right: 0px;
        color: white;
        padding: 6px;
        background-image: url('/src/images/toolbar_bg.gif');
    }

    #statusContainer div {
        font-size: 10pt;
        float: left;
    }
</style>
