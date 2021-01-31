<template>
    <div class="blockly_app">
        <BlocklyComponent class="blockly_component" :cell="cell" :store="store" :options="options"/>
    </div>
</template>

<script>
  import BlocklyComponent from './components/BlocklyComponent.vue'
  import BlocklyJS from 'blockly/javascript';
  import './blocks/pattern';

  export default {
    name: "VueBlockly",
    components: {
      BlocklyComponent
    },
    data() {
      return {
        code: '',
        options: {
          grid:
            {
              spacing: 25,
              length: 3,
              colour: '#ccc',
              snap: true
            },
          move: {
            scrollbars: true,
            drag: true,
            wheel: true
          },
          zoom:
            {
              controls: true,
              wheel: true,
              startScale: 0.65,
              maxScale: 3,
              minScale: 0.3,
              scaleSpeed: 1.2
            },
          readOnly: true,
          media: './media/',
          trashcan: true,
          toolbox:
            `<xml>
          <category name="Pattern" colour="%{BKY_LOOPS_HUE}">
            <block type="pattern_freemarker_interpolate"></block>
            <block type="pattern_freemarker_text"></block>
          </category>
          <category name="Logic" colour="%{BKY_LOGIC_HUE}">
            <block type="controls_if"></block>
            <block type="logic_compare"></block>
            <block type="logic_operation"></block>
            <block type="logic_negate"></block>
            <block type="logic_boolean"></block>
          </category>
          <category name="Loops" colour="%{BKY_LOOPS_HUE}">
            <block type="controls_repeat_ext">
              <value name="TIMES">
                <block type="math_number">
                  <field name="NUM">10</field>
                </block>
              </value>
            </block>
            <block type="controls_whileUntil"></block>
          </category>
          <category name="Math" colour="%{BKY_MATH_HUE}">
            <block type="math_number">
              <field name="NUM">123</field>
            </block>
            <block type="math_arithmetic"></block>
            <block type="math_single"></block>
          </category>
          <category name="Text" colour="%{BKY_TEXTS_HUE}">
            <block type="text"></block>
            <block type="text_length"></block>
            <block type="text_print"></block>
          </category>
          <category name="Variables" custom="VARIABLE" colour="%{BKY_VARIABLES_HUE}">
            </category>
        </xml>`
        }
      }
    },
    props: ['cell', 'store'],
    methods: {
      showCode() {
        this.code = BlocklyJS.workspaceToCode(this.$refs[ "foo" ].workspace);
      }
    },
    mounted() {
      if (this.cell == null){

      }
    }
  }
</script>

<style scoped>
    .blockly_app {
        /* font-family: 'Avenir', Helvetica, Arial, sans-serif;
         -webkit-font-smoothing: antialiased;
         -moz-osx-font-smoothing: grayscale;
         color: #2c3e50;*/
    }

    .blockly_component {
        height: 100%;
    }
</style>