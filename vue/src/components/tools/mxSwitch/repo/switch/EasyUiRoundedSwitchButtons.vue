<template>
    <SwitchButton class="rounded" :click="sendSetting(checked)" v-model="checked"></SwitchButton>
</template>

<script>
  //import { EventBus } from '../../../../../event-bus.js';
  export default {
    name: "EasyUiRoundedSwitchButtons",
    data() {
      return {
        checked: this.initChecked()
      }
    },
    props: ['cell', 'store', 'value'],
    methods: {
      sendSetting: function(value) {
        if (typeof this.cell === 'undefined') {
          this.$emit('click')
          return;
        }
        let data = {};
        this.cell.data.setting._.state = value;
        this.store.dispatch("sendSetting2VX", this.cell);
      },
      initChecked: function() {
        if (typeof this.value === 'undefined') {
          return this.cell.getData().setting._.state;
        } else {
          return this.value;
        }
      }
    }
  }
</script>

<style>
    .rounded.switchbutton {
        border-radius: 30px;
    }

    .rounded.switchbutton .switchbutton-handle {
        border-radius: 30px;
    }
</style>