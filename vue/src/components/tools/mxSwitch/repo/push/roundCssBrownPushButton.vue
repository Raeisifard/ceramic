<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssBrownPushButton",
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
        box-shadow: 0px 0px 0px 2px #a6827e;
        background: linear-gradient(to bottom, #7d5d3b 5%, #634b30 100%);
        background-color: #7d5d3b;
        border-radius: 16px;
        border: 1px solid #54381e;
        display: inline-block;
        cursor: pointer;
        color: #ffffff;
        font-family: Arial;
        font-size: 19px;
        padding: 15px 15px;
        text-decoration: none;
        text-shadow: 0px 1px 2px #4d3534;
    }

    .myButton:hover {
        background: linear-gradient(to bottom, #634b30 5%, #7d5d3b 100%);
        background-color: #634b30;
    }

    .myButton:active {
        position: relative;
        top: 1px;
    }
</style>