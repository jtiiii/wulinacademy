import './styles/index.css';
import VueBaseComponents, {BaseModel as Model} from "./scripts/VueBaseComponents";
import Vue from "vue";
import logo from "./images/logo-new.png";
import navIcon from "./icons/list-icon.png";
import setIcon from "./icons/setting-icon.png";
import Vuex from "vuex";
import VueI18n from "vue-i18n";
import I18n,{ I18nLanguage, I18nCodes } from './i18n/i18n';
import VueRouter from 'vue-router';
import router from './scripts/router';


(() =>{
    //使用Vuex
    Vue.use(Vuex);
    //使用vue-i18n
    // Vue.use(VueI18n);
    //使用vue-router
    Vue.use(VueRouter);

    //将Base组件注册为全局组件
    for(let name in VueBaseComponents){
        Vue.component(name,VueBaseComponents[name]);
    }
})();

// const i18n = new VueI18n(I18n);

function getMessage( name ){
    let message = I18n.getLocaleMessage(I18n.locale);
    name.split('.').forEach( key => {
        message = message[key]
    });
    return message;
}

function I18nListItems( items ){
    let map = {};
    items.forEach(item => {
        let m = new Model.ListItem(item.id,getMessage(item.i18nPath));
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
            item.model.text = getMessage(item.i18nPath);
        });
    }
};

let navObj = {
    "home": 'index.nav.home',
    "about": 'index.nav.about',
    "news": 'index.nav.news',
    "channel": 'index.nav.channel',
    "apply": 'index.nav.apply'
};

let nav = undefined;
(() => {
    let navArr = [];
    for(let key in navObj){
        navArr.push({id: key, i18nPath: navObj[key]});
    }
    nav = new I18nListItems(navArr);
})();


function I18nGroupMenu( groups ){
    groups.forEach( group => {
        group.modelItem = new I18nListItems(group.items);
        group.model = new Model.GroupMenu(getMessage(group.i18nPath), group.modelItem.toArray());
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
            group.model.name = getMessage(group.i18nPath);
            group.modelItem.refresh();
        });
    }
};


let languagesItems = [];
I18nCodes.forEach( code => {
    languagesItems.push( new Model.ListItem(code,I18nLanguage[code]));
});
let setGroup = new I18nGroupMenu([]);
let sets = setGroup.toArray();
let languageGroup = new Model.GroupMenu(getMessage('index.sets.language'), languagesItems);
sets.push(languageGroup);

let Locale ={};
Object.defineProperty(Locale,'value',{
    get: function(){
        return I18n.locale;
    },
    set: function( value ){
        I18n.locale = value;
        nav.refresh();
        setGroup.refresh();
        languageGroup.name = getMessage('index.sets.language');
    }
});
window.topBar = new Vue({
    el: '#topBar',
    data:{
        nav: nav.toArray(),
        logoSrc: logo,
        navIcon: navIcon,
        settingIcon: setIcon,
        sets: sets,
        currentTitle: getMessage('index.nav.home'),
        setClick: ()=>{},
    },
    methods: {
        navClick: function( item ){
            this.currentTitle = item.text;
            router.push('/' + item.id);
            this.$refs.navigation.hideMenu();
        }
    },
    mounted: function(){
        router.beforeEach( (to, from, next ) => {
            switch (to.fullPath) {
                case '/':
                    next('/home');
                    break;

                case '/error':
                    this.currentTitle = getMessage('index.nav.error');
                    next();
                    break;

                default:
                    let item =nav.map[to.fullPath.substring(1)];
                    if( !item ){
                        next("/error");
                    }else{
                        this.currentTitle = item.model.text;
                        next();
                    }
            }
        });
    }
});

window.content = new Vue({
    router,
    el: '#content',
});

window.Vue = Vue;

window.title = new Vue({
    el: 'title',
    i18n : I18n
});

window.i18n = I18n;