<template>
    <div style="width:100%;height:800px;">
        <editor v-model="content"></editor>
    </div>
</template>
<script>
    import Editor from '../scripts/components/news-editor/NewsEditor.vue';

    export default {
        components:{
            'editor': Editor
        },
        data(){
            return {
                content: null
            };
        }
    }
</script>
<style scoped>
</style>