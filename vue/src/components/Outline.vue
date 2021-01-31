<template>
    <!-- Creates a container for the outline -->
    <div id="outlineContainer">
    </div>
</template>

<script>
  export default {
    name: 'Outline',
    props: {
      //msg: String
    },
    mounted(){
      let graph = this.$store.getters.getEditor.graph;
      let outline = this.$el;
      // Creates the outline (navigator, overview) for moving
      // around the graph in the top, right corner of the window.
      let outln = new mxOutline(graph, outline);

      // To show the images in the outline, uncomment the following code
      //outln.outline.labelsVisible = true;
      //outln.outline.setHtmlLabels(true);
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #outlineContainer {
        box-sizing: initial;
        position:absolute;
        overflow:hidden;
        top:36px;
        right:0px;
        width:200px;
        height:140px;
        background:transparent;
        border-style:solid;
        border-color:black;
    }
</style>
