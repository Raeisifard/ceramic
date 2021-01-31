<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssGreenPushButton",
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
        box-shadow: 0px 0px 2px 2px #caefab;
        background:linear-gradient(to bottom, #77d42a 5%, #5cb811 100%);
        background-color:#77d42a;
        border-radius:16px;
        border:1px solid #268a16;
        display:inline-block;
        cursor:pointer;
        color:#306108;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 0px #aade7c;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #5cb811 5%, #77d42a 100%);
        background-color:#5cb811;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>