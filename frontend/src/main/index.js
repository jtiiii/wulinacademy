import './styles/index.css';
import VueBaseComponents from "./scripts/VueBaseComponents";
import Vue from "vue";
import Vuex from "vuex";
import VueI18n from "vue-i18n";
import I18n,{ I18nLocale } from './scripts/i18n';
import VueRouter from 'vue-router';
import router from './scripts/router';

import TopBar from './scripts/components/TopBar.vue';
import ContentPage from './scripts/components/ContentPage.vue';
import Footer from './scripts/components/Footer.vue';


(() =>{
    //使用Vuex
    Vue.use(Vuex);
    //使用vue-i18n
    Vue.use(VueI18n);
    //使用vue-router
    Vue.use(VueRouter);

    //将Base组件注册为全局组件
    for(let name in VueBaseComponents){
        Vue.component(name,VueBaseComponents[name]);
    }
})();

window.Vue = Vue;

window.title = new Vue({
    el: 'title',
    i18n : I18n
});
const fontFamily = {
    en: 'Myriad Pro',
    //华文细黑
    zh: 'STHeiti'
};
window.vueBody = new Vue({
    el: '#body',
    components: {
        "v-top-bar": TopBar,
        "v-content": ContentPage,
        "v-footer": Footer
    },
    data:{
        bodyStyle:{
            'font-family': fontFamily.es
        },
    },
    methods: {
        changePage: function( item ){
            router.push('/' + item.id);
        },
        changeFontFamily: function( locale ){
            let code = locale.substring(0,locale.indexOf("-"));
            switch (code){
                case 'zh': this.bodyStyle["font-family"] = fontFamily.zh; break;
                case 'en': this.bodyStyle["font-family"] = fontFamily.en; break;
                default: this.bodyStyle["font-family"] = fontFamily.en;
            }
        }
    },
    mounted: function(){
        let preProcessRouter = ()=>{
            router.beforeEach( (to, from, next ) => {
                switch (to.fullPath) {
                    case '':
                    case '/':
                        next('/news');
                        break;
                    case '/error':
                        this.$refs.topBar.currentTap = 'error';
                        next();
                        break;
                    default:
                        if( !this.$refs.topBar.changeTap(to.fullPath.substring(1)) ){
                            next("/error");
                        }else{
                            next();
                        }
                }
            });
            if(!router.currentRoute.name){
                router.push('/news');
            }
            return true;
        };
        let preProcessFontFamily = ()=>{
            I18nLocale.addResolve( (old, value) => {
                this.changeFontFamily(value);
            });
        };
        let preProcessLocale= ()=>{
            I18nLocale.code = getLocale();
        };

        //预处理序列
        new Promise( resolve => resolve() )
            .then( preProcessRouter )
            .then( preProcessFontFamily )
            .then( preProcessLocale );
    }
});

function getLocale(){
    return window.navigator.language;
}

window.i18n = I18n;
window.I18nLocale = I18nLocale;
window.router = router;