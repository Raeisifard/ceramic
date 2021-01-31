<template>
   <span>
       function mxWindow(	title,
                            content,
                            x,
                            y,
                            width,
                            height,
                            minimizable,
                            movable,
                            replaceNode,
                            style	)
   </span>
</template>
<script>
  export default {
    name: 'mxWindow2',
    components: {},
    data() {
      return {}
    },
    props: [ 'cell', 'cmd',
        'title',
        'content',
        'x',
        'y',
        'width',
        'height',
        'minimizable',
        'movable',
        'replaceNode',
        'style'
    ],
    watch: {
      cmd: function(val) {

      }
    },
    computed: {
      cellType: function() {
        return 'dialog-' + this.cell.getType().toLowerCase();
      }
    },
    methods: {
      getCmd: function() {
        return this.$store.getters.getCmd;
      }
    },
    mounted() {
      this.$store.dispatch("setDialog", this.$refs.dl);
      //window.easyDialog = this.$refs.dl;
      if (this.cell)
        this.isCell = true;
    }
  }
</script>

<style scoped>

</style>
