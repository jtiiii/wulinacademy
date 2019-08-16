<template>
    <div class="home">
        <v-modal ref="modal" :show="modal.show" @click.native="hideModal">
            <img class="wechatQRCode" :src="wechatQRCode">
        </v-modal>

        <div class="broadcast-list">
            <v-cover v-for="cover in covers" v-show="cover.show" :src="cover.src" class="broadcast">
                <div>
                    <h2>{{ cover.title}}</h2>
                    <span> {{ cover.preview }}</span>
                </div>
            </v-cover>
        </div>

        <div class="guide-line">{{ $t('index.nav.about') }}</div>

        <div class="about">
            <a @click="goAbout" class="about-col">{{ $t('about.nav.history') }}</a>
            <a @click="goAbout" class="about-col">{{ $t('about.nav.contact') }}</a>
            <a @click="goAbout" class="about-col">{{ $t('about.nav.map') }}</a>
        </div>

        <div class="guide-line">{{ $t('index.nav.channel') }}</div>

        <div class="channels">
            <v-logo :logo="channel.facebook"></v-logo>
            <v-logo :logo="channel.instagram"></v-logo>
            <v-logo :logo="channel.wechat" @click="showQR"></v-logo>
            <v-logo :logo="channel.twitter"></v-logo>
            <v-logo :logo="channel.youtube"></v-logo>
            <v-logo :logo="channel.weibo"></v-logo>
        </div>
<!--        <hr/>-->
    </div>
</template>
<script type="text/javascript">
    import Modal from '../components/Modal.vue'
    import NewsService from '../scripts/api/NewsService';
    import Logo from '../components/logo.vue';
    import ChannelData from '../scripts/ChannelData';
    import welcomePic from '../assets/images/welcome-default.jpeg';
    import FComponents from 'f-vue-components';
    // import noPic from '../images/news/no-pic.png';

    export default {
        components:{
            'v-logo': Logo,
            'v-modal': Modal,
            'v-cover': FComponents.Cover
        },
        data: ()=>{
            return {
                msg: 'Home',
                news: [],
                channel: ChannelData,
                covers:[{
                    src: welcomePic,
                    title: '欢迎来到武林书画院',
                    preview: '',
                    show: true,
                    eventDate: '',
                    index: 0,
                }],
                showCoverIndex: 0,
                modal: {
                    show: false
                },
                wechatQRCode: ChannelData.wechat.QR,
            };
        },
        methods: {
            // getLastNews(){
            //     return NewsService.pageSearch({pageSize: 1,pageNum: 0}).then( pageData => {
            //         return pageData.content[0];
            //     });
            // },
            // printArticle( news ){
            //     // this.news = Model.News.of( news );
            // },
            hideModal: function(){
                this.modal.show = false;
            },
            showQR: function(){
                this.modal.show = true;
            },
            goAbout: function(){
                this.$router.push('about');
            },
            changeCover(){
                this.covers[this.showCoverIndex].show =false;
                this.showCoverIndex++;
                this.covers[this.showCoverIndex].show =true;
            }
            // startBroadcast(){
            //     let loopCover = setInterval(()=>{
            //
            //         let index = this.news[this.cover.index];
            //         this.cover.src = index.thumbnail || noPic;
            //         this.cover.title = index.title;
            //         this.cover.preview = index.preview;
            //         this.cover.index ++;
            //         if(this.cover.index >= this.news.length){
            //             this.cover.index = 0;
            //         }
            //     },5000);
            // }
        },
        mounted: function(){
            // this.getLastNews().then( news => this.printArticle( news ));
        },
        created(){
            // NewsService.pageSearch({search: '',pageNum:0,pageSize:5}).then( page => { this.news = page.content;} )
            //     .then(()=>{
            //         this.startBroadcast();
            //     });

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
        cursor: pointer;
    }
    .about{
        display: flex;
        flex-wrap: wrap;
    }
    @media screen and (max-width:480px){
        .about{
            flex-direction: column;
        }
        .broadcast{
            max-height: 14.375rem; /* 230px */
        }
    }
    @media screen and (min-width:480px){
        .about{
            flex-direction: row;
        }

        .broadcast{
            max-width: 56.25rem; /* 900px */
            max-height: 37.5rem; /* 600px */
            margin: 0.625rem 2rem; /* 10px */
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
        border-radius: 0;
    }

    .channels{
        display: flex;
        flex-flow: row wrap;
        align-items: center;
        justify-content: center;
    }
</style>