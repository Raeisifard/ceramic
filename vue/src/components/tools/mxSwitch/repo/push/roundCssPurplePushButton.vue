<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssPurplePushButton",
    props: ['cell', 'store', 'value'],
    methods: {
      sendSetting: function(value) {
        this.$emit('click');
        if (!this.cell)
          return;
        this.store.dispatch("sendSetting2VX", this.cell);
      }
    }
  }
</script>

<style scoped>
    .myButton {
        box-shadow: 0px 0px 0px 2px #efdcfb;
        background:linear-gradient(to bottom, #dfbdfa 5%, #bc80ea 100%);
        background-color:#dfbdfa;
        border-radius:16px;
        border:1px solid #c584f3;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 2px #9752cc;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #bc80ea 5%, #dfbdfa 100%);
        background-color:#bc80ea;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>