<template>
    <div class="center">
        <input type="checkbox" name="" @click="sendSetting($event)" v-model="checked">
    </div>
</template>

<script>
  export default {
    name: "blueSubscribeButtons",
    data() {
      return {
        checked: this.initChecked()
      }
    },
    props: [ 'cell', 'store','value' ],
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
    body {
        margin:0;
        padding:0;
        background:#151515;
    }

    .center {
        /*position:absolute;
        left:50%;
        top:50%;
        transform:translate(-50%, -50%);*/
        transform: scale(.5,.5);
    }

    input[type="checkbox"] {
        cursor: pointer;
        margin:10px;
        position:relative;
        width:120px;
        height:40px;
        -webkit-appearance: none;
        background: linear-gradient(0deg, #333, #000);
        outline: none;
        border-radius: 20px;
        box-shadow: 0 0 0 4px #353535, 0 0 0 5px #3e3e3e, inset 0 0 10px rgba(0,0,0,1);
    }

    input:checked[type="checkbox"] {
        background: linear-gradient(0deg, #70a1ff, #1e90ff);
        box-shadow: 0 0 0 4px #353535, 0 0 0 5px #3e3e3e, inset 0 0 10px rgba(0,0,0,1);
    }

    input[type="checkbox"]:before {
        content:'';
        position:absolute;
        top:0;
        left:0;
        width:80px;
        height:40px;
        background: linear-gradient(0deg, #000, #6b6b6b);
        border-radius: 20px;
        box-shadow: 0 0 0 1px #232323;
        transform: scale(.98,.96);
        transition:.5s;
    }

    input:checked[type="checkbox"]:before {
        left:40px;
    }

    input[type="checkbox"]:after{
        content:'';
        position:absolute;
        top:calc(50% - 2px);
        left:70px;
        width:4px;
        height:4px;
        background: linear-gradient(0deg, #6b6b6b, #000);
        border-radius: 50%;
        transition:.5s;
    }

    input:checked[type="checkbox"]:after {
        left:110px;
    }

    h1 {
        margin:0;
        padding:0;
        font-family: sans-serif;
        text-align:center;
        color:#fff;
        font-size:16px;
        padding:15px 0;
        text-transform: uppercase;
        letter-spacing:4px;
    }
</style>