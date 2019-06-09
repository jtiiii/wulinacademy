<template>
    <div class="home">
        <v-modal ref="modal" :show="modal.show" @click.native="hideModal">
            <img class="wechatQRCode" :src="wechatQRCode">
        </v-modal>
        <div class="broadcast">
<!--            <div class="broadcast-img">-->
                <img class="broadcast-img" :src="news.thumbnail">
<!--            </div>-->
            <div class="broadcast-info">
                <span class="title">{{ news.title }}</span>
                <br/>
                <span class="time">{{ news.eventDate }}</span>
            </div>
        </div>
        <div class="guide-line">{{ $t('index.nav.about') }}</div>
        <div class="about">
            <a class="about-col">{{ $t('about.nav.history') }}</a>
            <a class="about-col">{{ $t('about.nav.contact') }}</a>
            <a class="about-col">{{ $t('about.nav.map') }}</a>
        </div>
        <div class="guide-line">{{ $t('index.nav.channel') }}</div>
        <div class="channels">
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.facebook"></v-logo>
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.instagram"></v-logo>
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.wechat" @click="showQR"></v-logo>
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.twitter"></v-logo>
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.youtube"></v-logo>
            <v-logo :width="'70px'" :height="'70px'" :logo="channel.weibo"></v-logo>
        </div>
<!--        <hr/>-->
    </div>
</template>
<script type="text/javascript">
    import Article from '../scripts/components/Article.vue';
    import Modal from '../scripts/components/BaseModal.vue'
    import Model from '../scripts/api/Model';
    import NewsService from '../scripts/api/NewsService';
    import Logo from '../scripts/components/logo.vue';
    import ChannelData from '../scripts/sample/channel-data';
    import i18n from '../scripts/i18n';
    import noPic from '../images/photo2.jpeg';

    const sampleData = Model.News.of({
        title: "欢迎来到武林书画院",
        preview: "test content",
        thumbnail:noPic,
        // eventDate: '2019-02-12'
    });


    export default {
        i18n,
        components:{
            'v-article': Article,
            'v-logo': Logo,
            'v-modal': Modal,
        },
        data: ()=>{
            return {
                msg: 'Home',
                //TODO It is a sample data
                news: sampleData,
                channel: ChannelData,
                modal: {
                    show: false
                },
                wechatQRCode: ChannelData.wechat.QR,
            };
        },
        methods: {
            getLastNews(){
                return NewsService.pageSearch({pageSize: 1,pageNum: 0}).then( pageData => {
                    return pageData.content[0];
                });
            },
            printArticle( news ){
                this.news = Model.News.of( news );
            },
            hideModal: function(){
                this.modal.show = false;
            },
            showQR: function(){
                this.modal.show = true;
            }
        },
        mounted: function(){
            // this.getLastNews().then( news => this.printArticle( news ));
        },
        created: function(){

        }
    };
</script>
<style scoped>
    .home{
        display: flex;
        flex-flow: column nowrap;
        align-items: center;
    }
    .wechatQRCode{
        width: 200px;
        position: relative;
    }
    .about-col{
        display: inline-block;
        margin: 9px 50px;
        letter-spacing: 5px;
        font-size: 18px;
    }
    .about{
        display: flex;
        flex-wrap: wrap;
    }
    @media screen and (max-width:480px){
        .about{
            flex-direction: column;
        }
    }
    @media screen and (min-width:480px){
        .about{
            flex-direction: row;
        }
    }

    .guide-line{
        width: 100%;
        flex: 1;
        margin: 10px 0;
        color: #fff;
        background: #cdcdcd;
        font-size: 20px;
        font-weight: bolder;
        height: 38px;
        line-height: 38px;
        letter-spacing: 6px;
    }
    .broadcast{
        display: flex;
        flex-flow: column nowrap;
        flex: 1;
        margin-bottom: 50px;
    }
    .broadcast-img{
        max-width: 100%;
        max-height: 37.5rem; /* 600px */
        /*margin: 30px 0 20px 0;*/
    }
    /*.broadcast-img > img{*/
    /*    !*width: 100%;*!*/
    /*    height: 100%;*/
    /*}*/

    .channels{
        display: flex;
        flex-flow: row wrap;
    }

    .broadcast-img:hover{
        opacity: .7;
    }
    .broadcast-info > .title{
        font-size: 20px;
        font-weight: bolder;
        margin: 10px 0;
        color: #000000a1;
        display: inline-block;
    }
    .broadcast-info > .time{
        font-family: sans-serif;
    }
</style>