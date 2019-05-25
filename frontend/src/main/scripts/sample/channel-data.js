import logo from '../../images/logo-new.png';
import twitterLogo from '../../icons/twitter-logo.png';
import youtubeLogo from '../../icons/youtube-logo.png';
import weiboLogo from '../../icons/weibo-logo.png';
import wechatLogo from '../../icons/wechat-logo.png';
import facebookLogo from '../../icons/facebook-logo.png';
import instagramLogo from '../../icons/instagram-logo.png';
import wechatQRCode from '../../images/wechat-QRcode.jpg';

export default {
    website:{
        src: logo
    },
    twitter: {
        src: twitterLogo,
        url: 'https://twitter.com/theWulinAcademy',
        i18key: 'channel.twitter',
        icon: '#wulin-twitter'
    },
    youtube: {
        src: youtubeLogo,
        url: 'https://www.youtube.com/channel/UCrZ3aTzVtNgTjETzTa90Ihw',
        i18key: 'channel.youtube',
        icon: '#wulin-youtube'
    },
    weibo: {
        src: weiboLogo,
        url: 'https://www.weibo.com/u/6603591190',
        i18key: 'channel.weibo',
        icon: '#wulin-weibo'
    },
    wechat:{
        src: wechatLogo,
        i18key: 'channel.wechat',
        QR: wechatQRCode,
        url: '#',
        icon: '#wulin-wechat'
    },
    facebook: {
        src: facebookLogo,
        url: 'https://www.facebook.com/theWulinAcademy/',
        i18key: 'channel.facebook',
        icon: '#wulin-facebook'
    },
    instagram: {
        src: instagramLogo,
        url: 'https://www.instagram.com/wulinacademyarts/',
        i18key: 'channel.instagram',
        icon: '#wulin-instagram'
    }
}