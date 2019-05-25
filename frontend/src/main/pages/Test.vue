<template>
    <div style="width:100%;height:800px;">
        <v-news-editor :mode="mode" @load="loadEditor" @submit="submit"></v-news-editor>
    </div>
</template>
<script>
    import NewsEditor from '../scripts/components/news-editor/NewsEditor.vue'

    export default {
        components:{
            "v-news-editor": NewsEditor
        },
        data(){
            return {
                mode: 'preview'
            }
        },
        methods:{
            loadEditor( load ){
                load({content:JSON.parse('{"ops":[{"insert":"sdf\\nsdf\\n\\n"}]}'),title:'test-title'});
            },
            submit( news){
                console.info(news);
            }
        }
    }
</script>
<style scoped>
</style>