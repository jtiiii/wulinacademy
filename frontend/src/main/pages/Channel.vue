<template>
    <div>
        <img :src="logoSrc"/>
        <nav>
            <a class="logo" :title="$t('channel.twitter')" ><img :src="channel.twitter.logo"></a>
            <a class="logo" :title="$t('channel.youtube')" ><img :src="channel.youtube.logo"></a>
            <a class="logo" :title="$t('channel.weibo')" ><img :src="channel.weibo.logo"></a>
            <a class="logo" :title="$t('channel.wechat')" ><img :src="channel.wechat.logo"></a>
            <a class="logo" :title="$t('channel.facebook')" ><img :src="channel.facebook.logo"></a>
            <a class="logo" :title="$t('channel.instagram')" ><img :src="channel.instagram.logo"></a>
        </nav>
    </div>
</template>
<script type="text/javascript">
    import i18n from '../scripts/i18n';
    import logo from '../images/logo-new.png';
    import twitterLogo from '../icons/twitter-logo.png';
    import youtubeLogo from '../icons/youtube-logo.png';
    import weiboLogo from '../icons/weibo-logo.png';
    import wechatLogo from '../icons/wechat-logo.png';
    import facebookLogo from '../icons/facebook-logo.png';
    import instagramLogo from '../icons/instagram-logo.png';
    export default {
        i18n,
        data: ()=>{
            return {
                logoSrc: logo,
                channel: {
                    twitter: {
                        address: '',
                        logo: twitterLogo
                    },
                    youtube: {
                        address: '',
                        logo: youtubeLogo
                    },
                    weibo: {
                        address: '',
                        logo: weiboLogo
                    },
                    wechat:{
                        logo: wechatLogo,
                    },
                    facebook: {
                        address: '',
                        logo: facebookLogo
                    },
                    instagram: {
                        address: '',
                        logo: instagramLogo
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
</style>