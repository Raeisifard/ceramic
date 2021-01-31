<template>
    <li class="upload">
        <slot><input type="file"/></slot>
        <a @click.prevent="showList">Open</a>
        <!--<a @click.prevent="showUpload">Open</a>-->
        <!--<button class="custom-upload" @click="showUpload">Add File</button>-->
    </li>
</template>

<script>
  import { EventBus } from '../../../event-bus.js';

  export default {
    name: "mxOpenXMLGraph",
    props: {
      max: {
        type: Number,
        default: 1
      },
      value: Array
    },

    data() {
      return {
        files: [],
        input: null,
        editor: this.$store.getters.getEditor,
        graph: this.$store.getters.getEditor.graph
      }
    },

    mounted() {
      // Find input
      this.input = this.$el.querySelector('input[type=file]');
      this.input.addEventListener('change', (event) => this.onFileSelection(event));
      this.input.style.display = 'none';

      // Set multiple attribute on input, if max is more than one
      if (this.max > 1) {
        this.input.setAttribute('multiple', 'multiple');
      } else {
        this.input.removeAttribute('multiple');
      }

      // Populate internal value, if external value is given,
      // attempt to populate external value by firing event if not
      if (this.value) {
        this.files = this.value
      } else {
        this.$emit('input', [])
      }

      this.editor.addAction('list', function(editor, cell)
      {
        var textarea = document.createElement('textarea');
        textarea.style.width = '400px';
        textarea.style.height = '400px';
        var enc = new mxCodec(mxUtils.createXmlDocument());
        var node = enc.encode(editor.graph.getModel());
        textarea.value = mxUtils.getPrettyXml(node);
        showModalWindow(graph, 'XML', textarea, 410, 440);
      });
    },

    methods: {

      /**
       * Executed, when the user selects a (or multiple) new file(s)
       * @returns {void}
       */
      onFileSelection(event) {
        let that = this;
        let input = event.target;
        let reader = new FileReader();
        reader.onload = function() {
          let data = reader.result;
          let xmlDoc = mxUtils.parseXml(data);
          let codec = new mxCodec(xmlDoc);
          codec.decode(xmlDoc.documentElement, that.graph.getModel());
          that.setVueComponents(that.graph.getModel());//Inject vue components to related mxCell
        };
        reader.readAsText(input.files[ 0 ]);

        // reset file input
        this.input.value = null;
      },

      setVueComponents(model) {
        EventBus.$emit("mx.set_vue_component", model);
      },

      /**
       * Removes the file with the given index
       * @param {number} index
       * @returns {void}
       */
      removeFile(index) {
        this.files.splice(index, 1)
      },

      showUpload() {
        const event = new MouseEvent('click', {
          'view': window,
          'bubbles': true,
          'cancelable': true
        });
        this.input.dispatchEvent(event)
      },

      showList() {
        const event = new MouseEvent('click', {
          'view': window,
          'bubbles': true,
          'cancelable': true
        });
        this.input.dispatchEvent(event)
      }
    },

    computed: {
      valid() {
        return this.files.length <= this.max
      }
    },

    watch: {
      files(files) {
        this.$emit('input', files)
      }
    }
  }
</script>

<style scoped>
    a {
        display: block;
        padding: 3px 19px;
        clear: both;
        font-family: arial;
        color: #444;
        text-decoration: none;
        cursor: pointer;
        font-weight: bold;
    }

    a:hover {
        background: #D3D3D3;
    }
</style>