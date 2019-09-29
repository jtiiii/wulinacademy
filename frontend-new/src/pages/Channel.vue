<template>
    <div style="width: 100%">
        <v-modal ref="modal" :show="modal.show" @click.native="hideModal" :size="'unlimited'">
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
    import FComponents from 'f-vue-components';
    import Logo from '../components/logo.vue';
    import ChannelData from '../scripts/ChannelData';

    export default {
        components:{
            'v-modal': FComponents.Modal,
            'v-logo': Logo
        },
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
            };
        }
    }
</script>
<style>
    @import url('../assets/styles/pages/Channel.css');
</style>