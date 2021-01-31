<template>
    <div>
        <div ref="patternContainer" class="patternContainer" :id="getId()">
            <div ref="patternDiv" class="patternDiv">

            </div>
            <div ref="blocklyDiv" class="blocklyDiv">

            </div>
        </div>
    </div>
</template>

<script>
  import Blockly from 'blockly';
  import '../blocks/pattern';
  import { EventBus } from '../../../../../event-bus.js';

  export default {
    name: 'BlocklyComponent',
    props: ['options', 'cell', 'store'],
    data() {
      return {
        workspace: null
      }
    },
    methods: {
      getId: function() {
        return this.cell.getType() + '.' + this.cell.getId()
      }
    },
    mounted() {
      let that = this;
      let options = this.$props.options || {};
      document.body.append(that.$refs[ "patternContainer" ]);
      this.workspace = Blockly.inject(that.$refs[ "blocklyDiv" ], options);
      let xml = "";
      EventBus.$on(`${that.cell.getType()}.${that.cell.getId()}`, message => {
        switch (message) {
          case "SVG_RESIZE":
            Blockly.svgResize(this.workspace);
            break;
          case "SVG_REMAKE":
            this.workspace.clear();
            let xml = Blockly.Xml.textToDom(that.cell.getData().xml);
            Blockly.Xml.domToWorkspace(xml, this.workspace);
            Blockly.svgResize(this.workspace);
            break;
        }
      });
      xml = Blockly.Xml.textToDom(that.cell.getData().xml);
      Blockly.Xml.domToWorkspace(xml, this.workspace);
      that.$el.append(that.$refs[ "patternContainer" ]);
      //Blockly.svgResize(that.$el.workspace);
      Blockly.svgResize(this.workspace);
    }
  }
</script>

<style>
    .patternContainer {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        position: absolute;
    }

    .patternDiv {
        width: 100%;
        height: 40px;
        display: flex;
        background-image: url("/src/images/pattern.png");
        background-repeat: repeat-x;
    }

    .blocklyDiv {
        /*height: 100%;*/
        /*width: 100%;
        height: 200px;*/
        text-align: left;
        /*display: flex;
        flex-grow: 1;*/
    }

    .blocklySvg {
        background-color: transparent;
    }
</style>
