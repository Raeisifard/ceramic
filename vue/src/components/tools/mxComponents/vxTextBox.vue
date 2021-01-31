<template>
    <div>
        <div style="margin-bottom:10px;display:inline-block;margin-right:1em">
            <Label for="t1" align="top">First Name:</Label>
            <TextBox inputId="t1" v-model="fname" iconCls="icon-man" placeholder="First name"></TextBox>
        </div>
        <div style="margin-bottom:10px;display:inline-block">
            <Label for="t2" align="top">Last Name:</Label>
            <TextBox inputId="t2" v-model="lname" placeholder="Last name"></TextBox>
        </div>
    </div>
</template>

<script>
  export default {
    name: "vxTextBox"
  }
</script>

<style scoped>

</style>