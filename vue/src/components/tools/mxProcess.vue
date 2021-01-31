<template>
    <mx-widget :sidebar="sidebar" :image="image" :label="label"></mx-widget>
</template>

<script>
  import mxWidget from '@/components/tools/mxComponents/mxWidget.vue';

  export default {
    name: 'mxProcess',
    components: {
      mxWidget
    },
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        image: "/src/images/icons48/gear.png",
        label:
        '<h1 style="margin:0px;">Process</h1><br>' +
        '<img src="/src/images/icons48/gear.png" width="48" height="48">' +
        '<br><select><option>Value1</option><option>Value2</option></select><br>'
      }
    },
    mounted(){
    }
  }
</script>

<style scoped>

</style>
