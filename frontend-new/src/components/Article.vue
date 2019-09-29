<template>
    <div class="article">
        <div class="news-sample" :class="ddClass" @click="click" @mouseenter="amplify" @mouseleave="reduce" >
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
    import Model from '../scripts/api/Model';
    import noPic from '../assets/images/no-pic.png';
    import StringUtils from '../scripts/utils/StringUtils';

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
<style lang="less">
    @import url('../assets/styles/pages/Article.less');
</style>