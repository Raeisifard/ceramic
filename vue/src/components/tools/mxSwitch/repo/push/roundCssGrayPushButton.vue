<template>
    <a href="#" class="myButton" @click="$emit('click')"> </a>
</template>

<script>
  export default {
    name: "RoundCssGrayPushButton",
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
        box-shadow: 0px 0px 0px 2px #899599;
        background:linear-gradient(to bottom, #ededed 5%, #bab1ba 100%);
        background-color:#ededed;
        border-radius:16px;
        border:1px solid #d6bcd6;
        display:inline-block;
        cursor:pointer;
        color:#3a8a9e;
        font-family:Arial;
        font-size:19px;
        padding:15px 15px;
        text-decoration:none;
        text-shadow:0px 1px 2px #e1e2ed;
    }
    .myButton:hover {
        background:linear-gradient(to bottom, #bab1ba 5%, #ededed 100%);
        background-color:#bab1ba;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>