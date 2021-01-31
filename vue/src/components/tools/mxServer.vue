<template>
    <mx-widget :sidebar="sidebar" :image="image" :label="label"></mx-widget>
</template>

<script>
  import mxWidget from '@/components/tools/mxComponents/mxWidget.vue';

  export default {
    name: 'mxServer',
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
        image: "/src/images/icons48/server.png",
        label:
        `<h1 style="margin:0px;">Server</h1>
         <img src="/src/images/icons48/server.png" width="48" height="48">
         <input type="text" size="10" value="127.0.0.1"/>`
      }
    }
  }
</script>