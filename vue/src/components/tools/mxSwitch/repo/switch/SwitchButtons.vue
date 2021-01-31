<template>
    <SwitchButton @click="sendSetting($event)" v-model="checked"></SwitchButton>
</template>

<script>
  export default {
    name: "SwitchButtons",
    data() {
      return {
        checked: this.initChecked()
      }
    },
    props: [ 'cell', 'store', 'value' ],
    methods: {
      sendSetting: function(event) {
        if (typeof this.cell === 'undefined') {
          this.$emit('click')
          return;
        }
        let data = {};
        this.cell.data.setting._.state = event.target.checked;
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

<style scoped>
</style>