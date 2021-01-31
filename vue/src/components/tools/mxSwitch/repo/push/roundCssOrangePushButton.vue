<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssOrangePushButton",
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
        box-shadow: 0px 0px 0px 2px #f9eca0;
        background:linear-gradient(to bottom, #f0c911 5%, #f2ab1e 100%);
        background-color:#f0c911;
        border-radius:16px;
        border:1px solid #e65f44;
        display:inline-block;
        cursor:pointer;
        color:#c92200;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 2px #ded17c;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #f2ab1e 5%, #f0c911 100%);
        background-color:#f2ab1e;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>