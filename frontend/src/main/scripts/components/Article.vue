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
    .article{
        max-width: 300px;
    }

    @media screen and (min-width:700px){

        article{
            text-align: left;
            float: left;
            max-width: 380px;
            height: calc(100% - 20px);
            overflow: hidden;
            margin: 10px
        }
        .news-item{
            position:relative;
            min-width: 43.75rem; /* 700px */
            height: 200px;
            margin: 0.625rem /* 10px */ 0;
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
        .news-title{
            font-weight: 900;
            color: #0009;
            margin: 10px 0;
            font-size: 18px;
        }

        .thumbnail{
            overflow: hidden;
            max-width: 280px;
            height: 180px;
            float: left;
            position: relative;
            margin:10px;
        }

        .news-preview{
            font-size: 15px;
            height: 105px;
            overflow: hidden;
        }
    }
    @media screen and (max-width:700px){
        .news-item{
            position:relative;
            max-width: 30rem; /* 480px */
            height: 350px;
            margin: 0.625rem /* 10px */ 0.625rem;
        }

        .news-simple{
            width: 100%;
            height: 100%;
            border-radius: 5px;
            display: flex;
            background: #efefef;
            flex-flow: column wrap;
            align-items: center;
        }

        article{
            flex: 1;
            overflow: hidden;
            margin: 10px
        }
        .news-title{
            font-weight: 900;
            color: #0009;
            margin: 0.625rem/* 10px */ 0;
            font-size: 1.5rem; /* 24px */
        }

        .thumbnail{
            margin: 0.625rem; /* 10px */
            overflow: hidden;
            max-width: 100%;
            height: 11.25rem;
        }


        .news-preview{
            font-size: 1rem; /* 16px */
        }
    }
    .news-time{
        position: relative;
        font-size:12px;
        margin:5px 0;
    }
    .thumbnail > img{
        width: 100%;
    }

</style>