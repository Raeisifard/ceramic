<template>
    <mx-widget :sidebar="sidebar" :image="image" :label="label"></mx-widget>
</template>

<script>
  import mxWidget from '@/components/tools/mxComponents/mxWidget.vue';

  export default {
    name: 'mxKeys',
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
        image: "/src/images/icons48/keys.png",
        label:
        '<h1 style="margin:0px;">Keys</h1><br>' +
        '<img src="/src/images/icons48/keys.png" width="48" height="48">' +
        '<br>' +
        '<button onclick="mxUtils.alert(\'generate\');">Generate</button>'
      }
    }
  }
</script>