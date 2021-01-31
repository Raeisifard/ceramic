<template>
    <div>
        <div style="background-color: #ddd">
            <LinkButton :plain="true">Home</LinkButton>
            <MenuButton text="File" :plain="true" iconCls="icon-file">
                <Menu @itemClick="onItemClick($event)">
                    <MenuItem value="new" text="New"></MenuItem>
                    <MenuItem text="Open">
                        <SubMenu>
                            <MenuItem value="word" text="Word"></MenuItem>
                            <MenuItem value="excel" text="Excel"></MenuItem>
                            <MenuItem value="ppt" text="PowerPoint"></MenuItem>
                        </SubMenu>
                    </MenuItem>
                    <MenuItem value="save" text="Save" iconCls="icon-save"></MenuItem>
                    <MenuItem value="print" text="Print" iconCls="icon-print" :disabled="true"></MenuItem>
                    <Menu-sep></Menu-sep>
                    <MenuItem value="exit" text="Exit"></MenuItem>
                </Menu>
            </MenuButton>
            <MenuButton text="Edit" :plain="true" iconCls="icon-edit">
                <Menu @itemClick="onItemClick($event)">
                    <MenuItem value="undo" text="Undo" iconCls="icon-undo"></MenuItem>
                    <MenuItem value="redo" text="Redo" iconCls="icon-redo"></MenuItem>
                    <MenuSep></MenuSep>
                    <MenuItem text="Cut"></MenuItem>
                    <MenuItem text="Copy"></MenuItem>
                    <MenuItem text="Paste"></MenuItem>
                    <MenuSep></MenuSep>
                    <MenuItem text="Toolbar">
                        <SubMenu>
                            <MenuItem text="Address"></MenuItem>
                            <MenuItem text="Link"></MenuItem>
                            <MenuItem text="Navigation Toolbar"></MenuItem>
                            <MenuItem text="Bookmark Toolbar"></MenuItem>
                            <MenuSep></MenuSep>
                            <MenuItem text="New Toolbar..."></MenuItem>
                        </SubMenu>
                    </MenuItem>
                    <MenuItem text="Delete" iconCls="icon-remove"></MenuItem>
                    <MenuItem text="Select All"></MenuItem>
                </Menu>
            </MenuButton>
            <MenuButton text="Help" :plain="true" iconCls="icon-help">
                <Menu>
                    <MenuItem text="Help"></MenuItem>
                    <MenuItem text="Update"></MenuItem>
                    <MenuItem text="About"></MenuItem>
                </Menu>
            </MenuButton>
            <MenuButton text="About" :plain="true">
                <Menu :noline="true">
                    <div style="padding:10px">
                        <img src="https://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
                    </div>
                </Menu>
            </MenuButton>
        </div>
        <div ref="blocklyDiv" class="blocklyDiv">
        </div>
    </div>
</template>

<script>
  import Blockly from 'blockly';
  import '../blocks/pattern';
  import { EventBus } from '../../../../../event-bus.js';

  export default {
    name: 'EditorComponent',
    props: ['options', 'cell', 'store'],
    data() {
      return {
        workspace: null
      }
    },
    methods: {
      onItemClick: function(event) {
        switch (event) {
          case 'save':
            window.top.postMessage({
              cmd: 'setCellCode',
              xml: Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(this.workspace)),
              code: Blockly[ 'JavaScript' ].workspaceToCode(this.workspace)
            }, '*');
            console.dir(Blockly[ 'JavaScript' ].workspaceToCode(this.workspace));
            break;
          case 'new':
            this.workspace.clear();
            break;
          case 'undo':
            Blockly.mainWorkspace.undo(false);
            break;
          case 'redo':
            Blockly.mainWorkspace.undo(true);
            break;
        }
      },
      getId: function() {
        return this.cell.getType() + '.' + this.cell.getId()
      },
      onresize: function(e) {
        let w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
        let h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
        //this.$el.style.backgroundColor = '#c0c0c0';
        this.$el.style.width = w + 'px';
        this.$el.style.height = h + 'px';
        this.$refs[ "blocklyDiv" ].style.width = w + 'px';
        this.$refs[ "blocklyDiv" ].style.height = h - 30 + 'px';
        Blockly.svgResize(this.workspace);
      }
    },
    mounted() {
      let that = this;
      let w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
      let h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
      //this.$el.style.backgroundColor = '#c0c0c0';
      this.$el.style.width = w + 'px';
      this.$el.style.height = h + 'px';
      this.$refs[ "blocklyDiv" ].style.width = w + 'px';
      this.$refs[ "blocklyDiv" ].style.height = h - 30 + 'px';
      let options = this.$props.options || {};
      if (!options.toolbox) {
        options.toolbox = this.$refs[ "blocklyToolbox" ];
      }
      this.workspace = Blockly.inject(this.$refs[ "blocklyDiv" ], options);
      window.top.postMessage({ cmd: 'getCellData' }, '*');
      window.onmessage = function(e) {
        if (e.data.cmd && e.data.cmd === 'setCellData') {
          let xml = Blockly.Xml.textToDom(e.data.xml);
          Blockly.Xml.domToWorkspace(xml, that.workspace);
          //Blockly.svgResize(that.workspace);
          //that.workspace.centerOnBlock(that.workspace.getAllBlocks(true)[0]);
          //console.dir(that.workspace.getAllBlocks(true));
        }
      };

      window.addEventListener('resize', this.onresize, false);
      //this.onresize();
      Blockly.svgResize(that.workspace);
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    /*.blocklyDiv {
        height: calc(100% - 30px);
        width: 100%;
        text-align: left;
    }*/
</style>
