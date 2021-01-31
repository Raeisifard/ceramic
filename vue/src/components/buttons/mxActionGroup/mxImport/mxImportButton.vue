<template>
    <!--<mx-button :editor="editor" action="import" label="Import"
               image="/src/images/import1.png"></mx-button>-->
    <li class="import">
        <a @click.prevent="_import">Import</a>
    </li>
</template>

<script>
  import mxButton from '@/components/buttons/mxComponents/mxButton.vue';
  import importForm from '@/components/buttons/mxActionGroup/mxImport/importForm.vue';

  export default {
    name: "mxImportButton",
    components: {
      mxButton,
      importForm
    },
    data: function() {
      return {
        editor: this.$store.getters.getEditor,
        importForm: importForm,
        wnd: null
      }
    },
    methods: {
      _import: function() {
        this.editor.execute("import");
      },
      showModalWindow: function(graph, title, content, width, height, that) {
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
        that.wnd = new mxWindow(title, content, x, y, width, height, false, true);
        that.wnd.setClosable(true);
        // Fades the background out after after the window has been closed
        that.wnd.addListener(mxEvent.DESTROY, function(evt) {
          graph.setEnabled(true);
          mxEffects.fadeOut(background, 50, true,
            10, 30, true);
        });

        graph.setEnabled(false);
        graph.tooltipHandler.hide();
        that.wnd.setVisible(true);
      }
    },
    mounted() {
      let editor = this.editor;
      let that = this;
      const _importForm = {
        components: {
          importForm
        },
        props: [ 'store' ],
        methods: {
          closeWindow(){
            that.wnd.destroy();
          }
        },
        template: '<import-form :store="store" @wndClose="closeWindow"></import-form>'
      };
      // Defines a new export action
      this.editor.addAction('import', function(editor, cell) {
        const ComponentClass = Vue.extend(_importForm);
        let instance = new ComponentClass({
          propsData: { store: that.$store }
        });
        instance.$mount();
        let graph = editor.graph;
        that.showModalWindow(graph, 'XML', instance.$el, 410, 440, that);//that.$refs.exportForm.$el.firstElementChild
      });
    }
  }
</script>

<style scoped>
    .import a {
        display: block;
        padding: 3px 19px;
        clear: both;
        font-family: arial;
        color: #444;
        text-decoration: none;
        cursor: pointer;
        font-weight: bold;
    }

    .import a:hover {
        background: #D3D3D3;
    }
</style>