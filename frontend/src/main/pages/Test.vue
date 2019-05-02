<template>
    <div>
        <login></login>
    </div>
</template>
<script>
    import BaseEditor from '../scripts/components/BaseEditor.vue';
    import Login from '../scripts/components/Login.vue';
    export default {
        components:{
            "login": Login,
            "base-editor": BaseEditor
        }
    }
</script>
<style scoped>
    .editor{
        width: 700px;
        height: 500px;
    }
</style>