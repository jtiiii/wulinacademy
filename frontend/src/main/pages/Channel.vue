<template>
    <div style="width: 100%">
        <v-modal ref="modal" :show="modal.show" @click.native="hideModal">
            <img class="wechatQRCode" :src="wechatQRCode">
        </v-modal>
        <ul>
            <li><a class="logo" :title="$t('channel.facebook')" target="_blank" :href="channel.facebook.url"><img :src="channel.facebook.logo"></a></li>
            <li><a class="logo" :title="$t('channel.instagram')" target="_blank" :href="channel.instagram.url"><img :src="channel.instagram.logo"></a></li>
            <li><a class="logo" :title="$t('channel.wechat')" @click="showQR" style="cursor:pointer;" ><img :src="channel.wechat.logo"></a></li>
            <li><a class="logo" :title="$t('channel.twitter')" target="_blank" :href="channel.twitter.url"><img :src="channel.twitter.logo"></a></li>
            <li><a class="logo" :title="$t('channel.weibo')" target="_blank" :href="channel.weibo.url"><img :src="channel.weibo.logo"></a></li>
            <li><a class="logo" :title="$t('channel.youtube')" target="_blank" :href="channel.youtube.url"><img :src="channel.youtube.logo"></a></li>
        </ul>
    </div>
</template>
<script type="text/javascript">
    import i18n from '../scripts/i18n';
    import BaseModal from '../scripts/components/BaseModal.vue';

    import logo from '../images/logo-new.png';
    import twitterLogo from '../icons/twitter-logo.png';
    import youtubeLogo from '../icons/youtube-logo.png';
    import weiboLogo from '../icons/weibo-logo.png';
    import wechatLogo from '../icons/wechat-logo.png';
    import facebookLogo from '../icons/facebook-logo.png';
    import instagramLogo from '../icons/instagram-logo.png';
    import wechatQRCode from '../images/wechat-QRcode.jpg';

    export default {
        components:{
            'v-modal': BaseModal
        },
        i18n,
        methods: {
            hideModal: function(){
                // this.$refs.modal.show(false);
                this.modal.show = false;
            },
            showQR: function(){
                this.modal.show = true;
            }
        },
        data: ()=>{
            return {
                modal: {
                    show: false
                },
                logoSrc: logo,
                wechatQRCode: wechatQRCode,
                channel: {
                    twitter: {
                        logo: twitterLogo,
                        url: 'https://twitter.com/theWulinAcademy'
                    },
                    youtube: {
                        logo: youtubeLogo,
                        url: 'https://www.youtube.com/channel/UCrZ3aTzVtNgTjETzTa90Ihw'
                    },
                    weibo: {
                        logo: weiboLogo,
                        url: 'https://www.weibo.com/u/6603591190'
                    },
                    wechat:{
                        logo: wechatLogo,
                    },
                    facebook: {
                        logo: facebookLogo,
                        url: 'https://www.facebook.com/theWulinAcademy/'
                    },
                    instagram: {
                        logo: instagramLogo,
                        url: 'https://www.instagram.com/wulinacademyarts/'
                    }
                }
            }
        }
    }
</script>
<style type="text/css" scoped>
    .logo{
        width: 100px;
        height: 100px;
        display: inline-block;
        margin: 15px;
    }
    .logo > img{
        height: 100%;
        width: 100%;
    }
    .wechatQRCode{
        width: 200px;
        position: relative;
    }
    ul {
        width: 100%;
        height: 100%;
        text-align: center;
        list-style-type: none;
        margin : 0 auto;
        padding: 0;
    }
    ul > li{
        text-align: center;
    }

</style>