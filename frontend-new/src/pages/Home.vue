<template>
    <div class="home">
        <div class="broadcast-list">
            <v-cover v-for="cover in covers" :key="cover.title" :mode="'row'" v-show="cover.show" :src="cover.src" class="broadcast">
                <h2>{{ cover.title}}</h2>
                <p>{{ cover.preview }}</p>
            </v-cover>
        </div>

        <div class="guide-line"><p>{{ $t('index.nav.about') }}</p></div>

        <div class="about">
            <a @click="goAbout" class="about-col">{{ $t('about.nav.history') }}</a>
            <a @click="goAbout" class="about-col">{{ $t('about.nav.contact') }}</a>
            <a @click="goAbout" class="about-col">{{ $t('about.nav.map') }}</a>
        </div>

        <div class="guide-line"><p>{{ $t('index.nav.channel') }}</p></div>

        <div class="channels">
            <v-logo :logo="channel.facebook" />
            <v-logo :logo="channel.instagram" />
            <v-logo :logo="channel.wechat" @click="showQR" />
            <v-logo :logo="channel.twitter" />
            <v-logo :logo="channel.youtube" />
            <v-logo :logo="channel.weibo" />

            <v-dialog :show="modal.show" :canClose="true" :size="'small'" @close="modal.show = false" >
                <img :alt="$t('channel.wechat')" class="wechatQRCode" :src="wechatQRCode">
            </v-dialog>
        </div>

    </div>
</template>
<script type="text/javascript">
    import NewsService from '../scripts/api/NewsService';
    import Logo from '../components/logo.vue';
    import ChannelData from '../scripts/ChannelData';
    import ImageService from "../scripts/api/ImageService";
    import welcomePic from '../assets/images/welcome/welcome-default.jpg';
    import FComponents from 'f-vue-components';
    import FUtils from 'fo-utils';
    import noPic from '../assets/images/no-pic.png';

    export default {
        components:{
            'v-logo': Logo,
            'v-dialog': FComponents.Dialog,
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
                    preview: '武林书画院南山院区',
                    show: true,
                    eventDate: ''
                }],
                showCoverIndex: 0,
                modal: {
                    show: false
                },
                defaultThumbnail: noPic,
                wechatQRCode: ChannelData.wechat.QR,
                orderTaskChain: null,
            };
        },
        methods: {
            hideModal: function(){
                this.modal.show = false;
            },
            showQR: function(){
                this.modal.show = true;
            },
            goAbout: function(){
                this.$router.push('about');
            },
            getThumbnail( thumbnail ){
                if(thumbnail){
                    return ImageService.getImageSrc(thumbnail);
                }
                return null;
            },
            changeCover(){
                let current = this.showCoverIndex;
                let next = this.showCoverIndex === this.covers.length - 1 ? 0: this.showCoverIndex + 1;
                if(current === next){
                    return;
                }
                this.covers[current].show =false;
                this.covers[next].show =true;
                this.showCoverIndex = next;
            },
            startBroadcast(){
                setInterval(()=>{
                    this.changeCover();
                },5000);
            },
            initCovers(){
                return NewsService.getTops(5 )
                    .then( newsList => {
                        newsList.forEach( news => {
                            this.covers.push({
                                src : this.getThumbnail(news.thumbnail) || this.defaultThumbnail,
                                title: news.title,
                                preview: news.preview,
                                show: false,
                                eventDate: news.eventDate
                            })
                        })
                    });
            },
        },
        mounted: function(){
            this.orderTaskChain.start();
        },
        created(){
            this.orderTaskChain = new FUtils.PromiseUtils.OrderTaskChain();
            this.orderTaskChain
                .addTask( this.initCovers.bind(this) )
                .addTask( this.startBroadcast.bind(this) );
        }
    };
</script>
<style lang="less">
    @import url('../assets/styles/pages/home.less');
</style>