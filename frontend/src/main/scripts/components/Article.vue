<template>
    <div class="news-item">
        <div class="news-simple" :class="ddClass" @click="click" @mouseenter="amplify" @mouseleave="reduce" >
            <!-- 缩略图 -->
            <figure class="thumbnail">
                <img :src="news.thumbnail? news.thumbnail: defaultThumbnail" />
            </figure>
            <!-- 标题、时间、预览信息 -->
            <article>
                <p class="news-title">{{ news.title }}</p>
                <p class="news-time">{{ time }}</p>
                <div class="news-preview">{{ fixPreviewLength( news.preview ) }}</div>
            </article>
        </div>
    </div>
</template>
<script type="text/javascript">
    import Model from '../api/Model';
    import noPic from '../../images/news/no-pic.png';
    import StringUtils from '../utils/StringUtils';

    function fixPreviewLength( preview ){
        let result = StringUtils.fixLength( preview, 120, "");
        if(preview && result.length > preview.length){
            result += "...";
        }
        return result;
    }

    export default {
        props:{
            news: {
                type: Object,
                required: false,
                default: () => {return Model.News.of({})}
            }
        },
        data(){
            return {
                ddClass : { expand: false , shrink: true, select: false},
                defaultThumbnail: noPic
            };
        },
        computed: {
            time: function(){
                return this.news.eventDate ? this.news.eventDate.substr(0,10): '';
            }
        },
        methods:{
            fixPreviewLength: fixPreviewLength,
            amplify: function( ){
                this.ddClass.expand = true;
                this.ddClass.shrink = false;
            },
            reduce: function( ){
                this.ddClass.expand = false;
                this.ddClass.shrink = true;
            },
            click: function(){
                this.$emit('click',this.news);
            }
        }
    }
</script>
<style scoped>
    .expand{
        padding: 8px 4px;
        animation: expand-animate 0.2s;
        left: -4px;
        top: -8px;
    }
    .shrink{
        animation: shrink-animate 0.2s;
        padding: 0;
        left: 0;
        top: 0;
    }
    article{
        text-align: left;
        float: left;
        width: 380px;
        height: calc(100% - 20px);
        overflow: hidden;
        margin: 10px
    }
    .article{
        width: 300px;
    }
    @keyframes expand-animate {
        0%{
            padding: 0;
            left: 0;
            top: 0;
        }
        100%{
            padding: 8px 4px;
            left: -4px;
            top: -8px;
        }
    }
    @keyframes shrink-animate {
        0%{
            padding: 8px 4px;
            left: -4px;
            top: -8px;
        }
        100%{
            padding: 0;
            left: 0;
            top: 0;
        }
    }
    .news-item{
        position:relative;
        width: 700px;
        height: 200px;
        margin: 10px 0;
    }
    .news-simple{
        width: 100%;
        height: 100%;
        position: absolute;
        border-radius: 5px;
        text-align: center;
        display: inline-block;
        background: #efefef;
        cursor: pointer;
    }
    .news-preview{
        font-size: 15px;
        height: 105px;
        overflow: hidden;
    }
    .news-time{
        position: relative;
        font-size:12px;
        margin:5px 0;
    }
    .news-title{
        font-weight: 900;
        color: #0009;
        margin: 10px 0;
        font-size: 18px;
    }
    .thumbnail{
        overflow: hidden;
        width: 280px;
        height: 180px;
        float: left;
        position: relative;
        margin:10px;
    }
    .thumbnail > img{
        width: 100%;
    }

</style>