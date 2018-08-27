import Vue from 'vue';
import VueI18n from 'vue-i18n';
import TopBar from './scripts/components/topbar.vue';
import logo from "./images/logo.png";

window.topBar = new Vue({
    el: '#topBar',
    components:{
        'topbar': TopBar
    },
    data:{
        nav: [{items:[
                {id: "about", text: 'About'},
                {id: 'channel', text: 'Channel'}
            ]}],
        logoSrc: logo,
        sets: [{items:[
                {id: 'zh_CN', text: '中文简体'},
                {id: 'en_US', text: 'English'}
            ], name:'language'}],
        currentTitle: 'About'
    }
});



// Vue.use(VueI18n);