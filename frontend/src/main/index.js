import './styles/index.css';
import VueBaseComponents, {BaseModel as Model} from "./scripts/VueBaseComponents";
import Vue from "vue";
import logo from "./images/logo-new.png";
import navIcon from "./icons/list-icon.png";
import setIcon from "./icons/setting-icon.png";
import footerLogo from './images/footer-logo.jpg';
import Vuex from "vuex";
import VueI18n from "vue-i18n";
import I18n,{ I18nLanguage, I18nCodes, I18nLocale } from './scripts/i18n';
import VueRouter from 'vue-router';
import router from './scripts/router';


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

function I18nListItems( items ){
    let map = {};
    items.forEach(item => {
        let m = new Model.ListItem(item.id,I18nLocale.getMessage(item.i18nPath));
        map[item.id] = item;
        item.model = m;
    });
    this.items = items;
    this.map = map;
}
I18nListItems.prototype = {
    constructor : I18nListItems,
    toArray : function(){
        let arr = [];
        this.items.forEach(item => {
            arr.push(item.model);
        });
        return arr;
    },
    refresh : function(){
        this.items.forEach(item => {
            item.model.text = I18nLocale.getMessage(item.i18nPath);
        });
    }
};
function I18nGroupMenu( groups ){
    groups.forEach( group => {
        group.modelItem = new I18nListItems(group.items);
        group.model = new Model.GroupMenu(group.id,I18nLocale.getMessage(group.i18nPath), group.modelItem.toArray());
    });
    this.groups = groups;
}
I18nGroupMenu.prototype = {
    constructor: I18nGroupMenu,
    toArray: function(){
        let arr = [];
        this.groups.forEach( group => {
            arr.push( group.model);
        });
        return arr;
    },
    refresh : function () {
        this.groups.forEach( group => {
            group.model.name = I18nLocale.getMessage(group.i18nPath);
            group.modelItem.refresh();
        });
    }
};


window.topBar = new Vue({
    el: '#topBar',
    data:{
        topBar: {
            'topBar': true,
            'fixed': false
        },
        navigation:{
            'navigation': true,
            'nav': true,
            'navFixed': false
        },
        setting: {
            'setting': true,
            'nav': true,
            'navFixed': false
        },
        nav: [],
        logoSrc: logo,
        navIcon: navIcon,
        settingIcon: setIcon,
        sets: [],
        currentTitle: I18nLocale.getMessage('index.nav.home'),
        currentTitleClass: {
            'currentTitle': true,
            'currentFixed': false
        }
    },
    methods: {
        navClick: function( item ){
            this.currentTitle = item.text;
            router.push('/' + item.id);
            this.$refs.navigation.hideMenu();
        },
        setClick: function( item, groupId ){
            switch (groupId) {
                case 'language':
                    I18nLocale.code = item.id;
                    break;
                default: return;
            }
        },
        tofixedOrRelative: function(){
            this.topBar.fixed = !this.topBar.fixed;
            let flag = this.topBar.fixed;
            content.content.contentFixed = flag;
            this.logoSrc = flag? undefined: logo;
            this.setting.navFixed = flag;
            this.navigation.navFixed = flag;
            this.currentTitleClass.currentFixed = flag;

        },
        handScroll: function(){
            let flag = this.topBar.fixed;
            if(!flag && window.scrollY >= 40){
                this.tofixedOrRelative();
            }else if(flag && window.scrollY <= 0){
                this.tofixedOrRelative();
            }
        }

    },
    created: function(){
        let navObj = {
            "home": 'index.nav.home',
            "about": 'index.nav.about',
            "news": 'index.nav.news',
            "channel": 'index.nav.channel',
            "apply": 'index.nav.apply'
        };

        let navArr = [];
        for(let key in navObj){
            navArr.push({id: key, i18nPath: navObj[key]});
        }
        this.$nav = new I18nListItems(navArr);
        this.nav = this.$nav.toArray();



        let languagesItems = [];
        I18nCodes.forEach( code => {
            languagesItems.push( new Model.ListItem(code,I18nLanguage[code]));
        });
        let setGroup = new I18nGroupMenu([]);
        this.sets = setGroup.toArray();
        let languageGroup = new Model.GroupMenu('language',I18nLocale.getMessage('index.sets.language'), languagesItems);

        this.sets.push(languageGroup);

        I18nLocale.addResolve(() => {
            this.$nav.refresh();
            setGroup.refresh();
            languageGroup.name = I18nLocale.getMessage('index.sets.language');
        });
    },
    mounted: function(){
        router.beforeEach( (to, from, next ) => {
            switch (to.fullPath) {
                case '/':
                    next('/home');
                    break;

                case '/error':
                    this.currentTitle = I18nLocale.getMessage('index.nav.error');
                    next();
                    break;

                default:
                    let item =this.$nav.map[to.fullPath.substring(1)];
                    if( !item ){
                        next("/error");
                    }else{
                        this.currentTitle = item.model.text;
                        next();
                    }
            }
        });
        window.addEventListener('scroll',this.handScroll);
    }
});

window.content = new Vue({
    router,
    data: {
        content: {
            'content': true,
            'contentFixed': false
        }
    },
    el: '#content',
});

window.footer = new Vue({
    el: '#footer',
    i18n: I18n,
    data:{
        footerLogo: footerLogo
    }
});

window.Vue = Vue;

window.title = new Vue({
    el: 'title',
    i18n : I18n
});

window.i18n = I18n;
window.I18nLocale = I18nLocale;