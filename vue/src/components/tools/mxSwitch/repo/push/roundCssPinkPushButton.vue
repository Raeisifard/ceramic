<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssPinkPushButton",
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
        box-shadow: 0px 0px 0px 2px #fbafe3;
        background:linear-gradient(to bottom, #ff5bb0 5%, #ef027d 100%);
        background-color:#ff5bb0;
        border-radius:16px;
        border:1px solid #ee1eb5;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 2px #c70067;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #ef027d 5%, #ff5bb0 100%);
        background-color:#ef027d;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>