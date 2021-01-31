<template>
    <div class="dialog-button">
        <LinkButton style="width:80px">Save</LinkButton>
        <LinkButton style="width:80px" @click="$emit('close_me')">Close</LinkButton>
    </div>
</template>

<script>
  export default {
    name: "dialogButton"
  }
</script>

<style scoped>

</style>