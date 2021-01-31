<template>
    <div style="display: contents;">
        <component :is="cmd.name" :cell="cell"/>
    </div>
</template>

<script>
    import triggerSetting from './dialogTriggerSetting';
    import pushSetting from './dialogPushSetting';

    export default {
        name: "dialogSwitch",
        components: {
            triggerSetting,
            pushSetting
        },
        props: ['cell', 'cmd'],
        methods: {
            closeMe: function () {
                this.$store.getters.getDialog.close();
                this.$store.dispatch("setCell", null);
            }
        },
        computed: {
            getCell: function () {
                return Vue.cell;
            }
        },
        mounted() {
            //this.$refs.dl.open()
        }
    }
</script>

<style scoped>

</style>