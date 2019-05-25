<template>
    <div style="width: 100%">
        <v-modal ref="modal" :show="modal.show" @click.native="hideModal">
            <img class="wechatQRCode" :src="wechatQRCode">
        </v-modal>
        <ul>
            <li><v-logo :logo="channel.facebook"></v-logo></li>
            <li><v-logo :logo="channel.instagram"></v-logo></li>
            <li><v-logo :logo="channel.wechat" @click="showQR"></v-logo></li>
            <li><v-logo :logo="channel.twitter"></v-logo></li>
            <li><v-logo :logo="channel.youtube"></v-logo></li>
            <li><v-logo :logo="channel.weibo"></v-logo></li>
        </ul>
    </div>
</template>
<script type="text/javascript">
    import i18n from '../scripts/i18n';
    import BaseModal from '../scripts/components/BaseModal.vue';
    import Logo from '../scripts/components/logo.vue';
    import ChannelData from '../scripts/sample/channel-data';

    export default {
        components:{
            'v-modal': BaseModal,
            'v-logo': Logo
        },
        i18n,
        methods: {
            hideModal: function(){
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
                logoSrc: ChannelData.website.logo,
                wechatQRCode: ChannelData.wechat.QR,
                channel: ChannelData
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
    /*.logo > img{*/
    /*    height: 100%;*/
    /*    width: 100%;*/
    /*}*/
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