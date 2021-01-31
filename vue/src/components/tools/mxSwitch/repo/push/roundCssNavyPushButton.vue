<template>
    <a href="#" class="myButton" @click="sendSetting(true)"> </a>
</template>

<script>
  export default {
    name: "RoundCssNavyPushButton",
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
        box-shadow: 0px 0px 2px 2px #9fb4f2;
        background:linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
        background-color:#7892c2;
        border-radius:16px;
        border:1px solid #4e6096;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 0px #283966;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #476e9e 5%, #7892c2 100%);
        background-color:#476e9e;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>