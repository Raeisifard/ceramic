<template>
        <div :class="{switch: true, active: active}" @click="active = !active">
          <div class='span'>{{active?"ON":"OFF"}}</div>
        </div>
</template>

<script>
  export default {
    name: "BlackWhiteYesNo",
    data() {
      return {active: false}
    },
    props: ['value'],
    methods: {
    }
  }
</script>

<style scoped>
    @import url(https://fonts.googleapis.com/css?family=Montserrat:700);

    body {
        background: #e8ebf2;
    }

    body a {
        font-family: 'Montserrat', sans-serif;
        color: #8b8b8b;
        text-decoration: none;
    }

    .switch {
        position: absolute;
        height: 60px;
        width: 170px;
        background: #8b8b8b;
        cursor: pointer;
        -moz-transform: skew(-30deg);
        -ms-transform: skew(-30deg);
        -webkit-transform: skew(-30deg);
        transform: skew(-30deg);
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        border-radius: 3px;
        margin: auto;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        -moz-transition: all 0.2s ease-out;
        -o-transition: all 0.2s ease-out;
        -webkit-transition: all 0.2s ease-out;
        transition: all 0.2s ease-out;
        text-indent: 45%;
        line-height: 60px;
        -moz-box-shadow: 0px 0.5px 2.5px #6f6f6f;
        -webkit-box-shadow: 0px 0.5px 2.5px #6f6f6f;
        box-shadow: 0px 0.5px 2.5px #6f6f6f;
    }

    .switch .span {
        font-family: 'Montserrat', sans-serif;
        -moz-transform: skew(30deg);
        -ms-transform: skew(30deg);
        -webkit-transform: skew(30deg);
        transform: skew(30deg);
        -moz-transition: all 0.2s ease-out;
        -o-transition: all 0.2s ease-out;
        -webkit-transition: all 0.2s ease-out;
        transition: all 0.2s ease-out;
        font-size: 30px;
        z-index: -1;
        color: #666;
    }

    .switch:before {
        -moz-transition: all 0.2s ease-out;
        -o-transition: all 0.2s ease-out;
        -webkit-transition: all 0.2s ease-out;
        transition: all 0.2s ease-out;
        position: absolute;
        content: "";
        height: 55px;
        width: 40px;
        top: 2.5px;
        left: 2.5px;
        cursor: pointer;
        background: #eee;
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        border-radius: 3px;
        -moz-box-shadow: 2.5px 0 5px #6f6f6f;
        -webkit-box-shadow: 2.5px 0 5px #6f6f6f;
        box-shadow: 2.5px 0 5px #6f6f6f;
    }

    .switch.active {
        -moz-transform: skew(30deg);
        -ms-transform: skew(30deg);
        -webkit-transform: skew(30deg);
        transform: skew(30deg);
        background: #9bc148;
    }

    .switch.active .span {
        -moz-transform: skew(-30deg);
        -ms-transform: skew(-30deg);
        -webkit-transform: skew(-30deg);
        transform: skew(-30deg);
        text-indent: 20%;
        color: #eee;
    }

    .switch.active:before {
        left: 127.5px;
        -moz-box-shadow: -2.5px 0px 5px #6f6f6f;
        -webkit-box-shadow: -2.5px 0px 5px #6f6f6f;
        box-shadow: -2.5px 0px 5px #6f6f6f;
    }

</style>