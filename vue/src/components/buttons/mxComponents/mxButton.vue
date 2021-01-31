<template>
    <button :class="{trans: transparent}">
        <img v-if="image" :src="image"/>
        {{label}}
    </button>
</template>

<script>
  export default {
    name: 'mxButton',
    props: {
      editor: {
        required: true
      },
      action: String,
      label: String,
      image: String,
      transparent: {
        type: Boolean,
        default: false
      }
    },
    mounted() {
      const that = this;
      mxEvent.addListener(this.$el, 'click', function(evt) {
        that.editor.execute(that.action);
      });
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    button {
        font-size: 10px;
    }

    img {
        width: 16px;
        height: 16px;
        vertical-align: middle;
        margin-right: 2px;
    }
    .trans {
        background : transparent;
        color : #FFFFFF;
        border : none;
    }
</style>
