// import VueBaseComponents from "../scripts/VueBaseComponents";
import Vue from "vue";
import Vuex from "vuex";
import VueI18n from "vue-i18n";
import I18n,{ I18nLocale } from '../scripts/i18n';
import VueRouter from 'vue-router';
import Index from '../pages/Index.vue';
import Common from '../scripts/Common';
import CheckService from '../scripts/api/CheckService';

window.systemCheck = CheckService;

(() =>{
    //使用Vuex
    Vue.use(Vuex);
    //使用vue-i18n
    Vue.use(VueI18n);
    //使用vue-router
    Vue.use(VueRouter);

    //将Base组件注册为全局组件
    // for(let name in VueBaseComponents){
    //     Vue.component(name,VueBaseComponents[name]);
    // }
})();

window.Vue = Vue;
window.Common = Common;
window.title = new Vue({
    el: 'title',
    i18n : I18n
});

window.main = new Vue({
    i18n : I18n,
    render( h ){
        return h(Index)
    },
    computed:{
        Index: function(){
            return this.$children[0];
        }
    }
}).$mount("#main");

function getLocale(){
    return window.navigator.language;
}

I18nLocale.code = getLocale();
window.i18n = I18n;
window.I18nLocale = I18nLocale;

