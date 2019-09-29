<template>
    <div>
<!--        <h3>{{ title }}</h3>-->
        <v-input-text v-model="titleTemp"/>
        <br/>
        <v-third-quill
                @init="initEditor"
        >
        </v-third-quill>
        <input type="hidden" v-model="watchNewsId" />
    </div>
</template>

<script>
    import NewsService from "../scripts/api/NewsService";
    import FComponents from 'f-vue-components';
    import _ from 'lodash';
    import BarrierTask from "../scripts/utils/BarrierTask";
    export default {
        name: "new-content",
        components:{
            'v-input-text': FComponents.Input.Text,
            'v-third-quill': FComponents.Third.Quill,
        },
        props:{
            newsId:{
                type: Number,
                required: false,
                default: 0,
            },
            title:{
                type: String,
                required: false,
                default: ''
            }
        },
        data(){return {
            content: null,
            editor: null,
            loadNews: null,
            barrierTask: null,
            init: null,
            titleTemp: ''
        };},
        computed:{
            watchNewsId(){
                this.loadNews();
                return this.newsId;
            }
        },
        methods:{
            initEditor( editor ){
                this.editor = editor;
                this.init();
            },
            loaderService(){
                return NewsService.getContent(this.newsId).then( data => {
                    this.content = data.content;
                });
            },
            loaderAndSet(){
                return this.loaderService().then( () => {
                    this.editor.setContents(this.content);
                    this.titleTemp = this.title;
                });
            }
        },
        created(){
            this.loadNews = _.debounce(this.loaderAndSet.bind(this), 50);
            this.barrierTask = new BarrierTask().addTask( () => {
                return new Promise( resolve => {
                    this.init = resolve;
                });
            });
            if(this.newsId){
                this.barrierTask.addTask( this.loaderService);
                this.titleTemp = this.title;
            }
            this.barrierTask.run().then( () => {
                this.editor.setContents(this.content);
            });
        }
    }
</script>

<style scoped>

</style>