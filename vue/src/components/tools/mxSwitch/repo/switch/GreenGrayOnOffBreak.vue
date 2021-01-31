<template>
        <div :class="{switch: true, active: active}" @click="sendSetting">
          <div class='span'>{{active?"ON":"OFF"}}</div>
        </div>
</template>

<script>
  export default {
    name: "GreenGrayOnOffBreak",
    data() {
      return {active: this.value || this.cell.data.setting._.state}
    },
    props: [ 'cell', 'store','value' ],
    methods: {
      sendSetting: function() {
        this.active = !this.active;
        if (typeof this.cell === 'undefined') {
          this.$emit('click')
          return;
        }
        let data = {};
        this.cell.data.setting._.state = this.active;
        this.store.dispatch("sendSetting2VX", this.cell);
      }
    }
  }
</script>

<style scoped>
    @import url(/src/css/greenGrayOnOffBreak/css.css);

    .switch {
        height: 30px;
        width: 85px;
        background: #8b8b8b;
        cursor: pointer;
        -moz-transform:  skew(-30deg);
        -ms-transform:  skew(-30deg);
        -webkit-transform:  skew(-30deg);
        transform:  skew(-30deg);
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
        text-indent: 25%;
        line-height: 30px;
        -moz-box-shadow: 0px 0.5px 2.5px #6f6f6f;
        -webkit-box-shadow: 0px 0.5px 2.5px #6f6f6f;
        box-shadow: 0px 0.5px 2.5px #6f6f6f;
    }

    .switch .span {
        font-family: 'Montserrat', sans-serif;
        -moz-transform:  skew(30deg);
        -ms-transform:  skew(30deg);
        -webkit-transform:  skew(30deg);
        transform:  skew(30deg);
        -moz-transition: all 0.2s ease-out;
        -o-transition: all 0.2s ease-out;
        -webkit-transition: all 0.2s ease-out;
        transition: all 0.2s ease-out;
        font-size: 15px;
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
        height: 27px;
        width: 20px;
        top: 1.5px;
        left: 1.5px;
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
        -moz-transform:  skew(30deg);
        -ms-transform:  skew(30deg);
        -webkit-transform:  skew(30deg);
        transform:  skew(30deg);
        background: #9bc148;
    }

    .switch.active .span {
        -moz-transform: skew(-30deg);
        -ms-transform: skew(-30deg);
        -webkit-transform: skew(-30deg);
        transform: skew(-30deg);
        text-indent: -20%;
        color: #eee;
    }

    .switch.active:before {
        left: 64px;
        -moz-box-shadow: -2.5px 0px 5px #6f6f6f;
        -webkit-box-shadow: -2.5px 0px 5px #6f6f6f;
        box-shadow: -2.5px 0px 5px #6f6f6f;
    }

</style>