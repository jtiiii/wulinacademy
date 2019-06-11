<template>
    <div style="width:100%;height:800px;">
        <v-article :news="news"></v-article>
    </div>
</template>
<script>
    import Article from '../scripts/components/Article.vue';

    export default {
        components:{
            'v-article': Article
        },
        data(){
            return {
                news:{
                    title: 'test',
                    preview: "Did you know? I wasn't understand you. But now, I want to help you to want you forgive me. I'm sorry. please show me your faith.",
                    eventDate: '2019-06-11T21:00:40'
                }
            };
        }
    }
</script>
<style scoped>
</style>