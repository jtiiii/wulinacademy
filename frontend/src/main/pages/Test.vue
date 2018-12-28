<template>
    <div>
        <base-editor class="editor"></base-editor>
    </div>
</template>
<script>
    import BaseEditor from '../scripts/components/BaseEditor.vue';
    export default {
        components:{
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