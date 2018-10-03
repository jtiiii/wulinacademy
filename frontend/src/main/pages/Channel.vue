<template>
    <div>
        <img :src="logoSrc"/>
        <nav>
            <a class="logo" :title="$t('channel.twitter')" :href="channel.twitter.address"><img :src="channel.twitter.logo"></a>
            <a class="logo" :title="$t('channel.youtube')" :href="channel.youtube.address"><img :src="channel.youtube.logo"></a>
            <a class="logo" :title="$t('channel.weibo')" :href="channel.weibo.address"><img :src="channel.weibo.logo"></a>
        </nav>
    </div>
</template>
<script type="text/javascript">
    import i18n from '../scripts/i18n';
    import logo from '../images/logo-new.png';
    import twitterLogo from '../icons/twitter-logo.png';
    import youtubeLogo from '../icons/youtube-logo.png';
    import weiboLogo from '../icons/weibo-logo.png';
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