<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssRedPushButton",
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
        box-shadow: 0px 0px 2px 2px #8a2a21;
        background:linear-gradient(to bottom, #c62d1f 5%, #f24437 100%);
        background-color:#c62d1f;
        border-radius:16px;
        border:1px solid #d02718;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 0px #810e05;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #f24437 5%, #c62d1f 100%);
        background-color:#f24437;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>