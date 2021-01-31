<template>
    <mx-widget :sidebar="sidebar" :image="image" :label="label"></mx-widget>
</template>

<script>
  import mxWidget from '@/components/tools/mxComponents/mxWidget.vue';

  export default {
    name: 'mxNewMail',
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
        image: "/src/images/icons48/mail_new.png",
        label:
        '<h1 style="margin:0px;">New Mail</h1><br>' +
        '<img src="/src/images/icons48/mail_new.png" width="48" height="48">' +
        '<br><input type="checkbox"/>CC Archive'
      }
    }
  }
</script>