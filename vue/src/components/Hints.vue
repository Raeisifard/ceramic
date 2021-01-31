<template>
    <div id="hints" v-if="!firstClick"></div>

</template>

<script>
  export default {
    name: 'Hints',
    data: function() {
      return {
        firstClick: false
      }
    },
    props: {
    },
    mounted() {
      let that = this;
      // Displays useful hints in a small semi-transparent box.
      let hints = this.$el;
      mxUtils.setOpacity(hints, 50);
      mxUtils.writeln(hints, '- Drag an image from the sidebar to the graph');
      mxUtils.writeln(hints, '- Doubleclick on a vertex or edge to edit');
      mxUtils.writeln(hints, '- Shift- or Rightclick and drag for panning');
      mxUtils.writeln(hints, '- Move the mouse over a cell to see a tooltip');
      mxUtils.writeln(hints, '- Click and drag a vertex to move and connect');

      // Attach an event handler to the document
      document.addEventListener("click", that.firstClickAct);
    },
    methods:{
      firstClickAct: function(){
        let that = this;
        that.firstClick = true;
        // Remove the event handler from the document
        document.removeEventListener("click", that.firstClickAct);
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #hints {
        box-sizing: initial;
        position: absolute;
        overflow: hidden;
        width: 230px;
        bottom: 56px;
        height: 76px;
        right: 20px;
        background: black;
        color: white;
        font-family: Arial;
        font-size: 10px;
        padding: 4px;
    }
</style>
