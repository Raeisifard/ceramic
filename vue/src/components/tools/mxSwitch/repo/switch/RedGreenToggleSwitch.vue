<template>
    <div class="container">
        <input :id='label' type='checkbox' @click="sendSetting($event)" v-model="checked">
        <label :for='label'>
            <div class='s'>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
                <div class='d'></div>
            </div>
        </label>

    </div>
</template>

<script>
  export default {
    name: "RedGreenToggleSwitch",
    data() {
      return {
        checked: this.value || this.cell.data.setting._.state,
        label: typeof this.value != 'undefined' ? "label" : "label_" + this.cell.getId()
      }
    },
    props: ['cell', 'store', 'value'],
    methods: {
      sendSetting: function(event) {
        if (typeof this.cell === 'undefined') {
          this.$emit('click')
          return;
        }
        let data = {};
        this.cell.data.setting._.state = event.target.checked;
        this.store.dispatch("sendSetting2VX", this.cell);
      }
    }
  }
</script>

<style scoped>
    * {
        user-select: none;
        transition: 0.5s;
    }

    html {
        background-color: #ebebeb;
    }

    body {
        overflow: hidden;
    }

    .container {
        transform: scale(.5);
    }

    label {
        width: 150px;
        height: 75px;
        background-color: #d55050;
        box-shadow: inset 0px 6px 0px 0px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
        position: absolute;
        /*top: 50%;
        left: 50%;*/
        transform: translate(-50%, -50%);
        z-index: 2;
    }

    input:checked ~ label {
        background-color: #50d5a1;
    }

    input:checked ~ label div.s {
        margin-left: 74px;
    }

    label div.s {
        width: 70px;
        height: 64px;
        background-color: #fafafa;
        border-radius: 8px;
        position: relative;
        top: 3px;
        left: 3px;
        box-shadow: 0px 5px 0px 0px #d2d2d2;
        cursor: pointer;
    }

    label div div.d {
        width: 7px;
        height: 7px;
        background-color: #ebebeb;
        position: relative;
        border-radius: 2.5px;
        box-shadow: inset 0px 1.5px 0px 0px rgba(0, 0, 0, 0.2);
        position: absolute;
    }

    label div div.d:nth-child(1) {
        left: 18px;
        top: 15px;
    }

    label div div.d:nth-child(2) {
        left: 32px;
        top: 15px;
    }

    label div div.d:nth-child(3) {
        left: 46px;
        top: 15px;
    }

    label div div.d:nth-child(4) {
        left: 18px;
        top: 28px;
    }

    label div div.d:nth-child(5) {
        left: 32px;
        top: 28px;
    }

    label div div.d:nth-child(6) {
        left: 46px;
        top: 28px;
    }

    label div div.d:nth-child(7) {
        left: 18px;
        top: 42px;
    }

    label div div.d:nth-child(8) {
        left: 32px;
        top: 42px;
    }

    label div div.d:nth-child(9) {
        left: 46px;
        top: 42px;
    }

    input {
        display: none;
        appearance: none;
    }
</style>