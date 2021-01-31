<template>
    <!-- <span>-->
    <!--<mx-button :editor="editor" action="export" label="Export"
               image="/src/images/export1.png"></mx-button>-->
    <!--<export-form ref="exportForm" v-show="false"></export-form>
    </span>-->
    <li class="export">
        <a @click.prevent="_export">Export</a>
    </li>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';
  import exportForm from '@/components/buttons/mxActionGroup/mxExport/exportForm.vue';

  export default {
    name: "mxExportButton",
    components: {
      mxButton,
      exportForm
    },
    data: function() {
      return {
        editor: this.$store.getters.getEditor,
        exportForm: exportForm
      }
    },
    methods: {
      _export: function() {
        this.editor.execute("export");
      },
      showModalWindow: function(graph, title, content, width, height) {
        let background = document.createElement('div');
        background.style.position = 'absolute';
        background.style.left = '0px';
        background.style.top = '0px';
        background.style.right = '0px';
        background.style.bottom = '0px';
        background.style.background = 'black';
        mxUtils.setOpacity(background, 50);
        document.body.appendChild(background);

        if (mxClient.IS_IE) {
          new mxDivResizer(background);
        }

        let x = Math.max(0, document.body.scrollWidth / 2 - width / 2);
        let y = Math.max(10, ( document.body.scrollHeight ||
          document.documentElement.scrollHeight ) / 2 - height * 2 / 3);
        let wnd = new mxWindow(title, content, x, y, width, height, false, true);
        wnd.setClosable(true);

        // Fades the background out after after the window has been closed
        wnd.addListener(mxEvent.DESTROY, function(evt) {
          graph.setEnabled(true);
          mxEffects.fadeOut(background, 50, true,
            10, 30, true);
        });

        graph.setEnabled(false);
        graph.tooltipHandler.hide();
        wnd.setVisible(true);
      }
    },
    mounted() {
      let editor = this.editor;
      let that = this;
      const _exportForm = {
        components: {
          exportForm
        },
        props: [ 'store' ],
        template: '<export-form :store="store"></export-form>'
      };
      // Defines a new export action
      this.editor.addAction('export', function(editor, cell) {
        const ComponentClass = Vue.extend(_exportForm);
        let instance = new ComponentClass({
          propsData: { store: that.$store }
        });
        instance.$mount();
        let graph = editor.graph;
        that.showModalWindow(graph, 'XML', instance.$el, 410, 440);//that.$refs.exportForm.$el.firstElementChild
      });
    }
  }
</script>

<style scoped>
    .export a {
        display: block;
        padding: 3px 19px;
        clear: both;
        font-family: arial;
        color: #444;
        text-decoration: none;
        cursor: pointer;
        font-weight: bold;
    }

    .export a:hover {
        background: #D3D3D3;
    }
</style>