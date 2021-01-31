<template>
  <!-- Creates a container for the splash screen -->
  <div id="splash">
    <center id="splashIn">
      <img src="/editors/images/loading.gif">
    </center>
  </div>
</template>

<script>
export default {
  name: 'Splash',
  props: {
    //msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  #splash {
    box-sizing: initial;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: white;
    z-index: 1;
  }
  #splashIn {
    padding-top:230px;
  }
</style>
