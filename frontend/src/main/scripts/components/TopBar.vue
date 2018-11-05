<template>
    <div>
        <div :class="topBar" :style="topBarLineStyle">
            <div :class="navigation" >
                <base-drop-down ref="navigation" :groups="nav" :item-click="navClick"><img class="icon" :src="navIcon"/></base-drop-down>
            </div>
            <img v-if="logoSrc" class="logo" :src="logoSrc" />
            <div :class="currentTitleClass" v-if="currentTitle"> {{ currentTitle }} </div>
            <div :class="setting">
                <base-drop-down ref="setting" :menu-direct="'right'" :groups="sets" :item-click="setClick"><img class="icon" :src="settingIcon"/></base-drop-down>
            </div>
        </div>
    </div>
</template>
<script type="text/javascript">
    import DropDown from './BaseDropDown.vue';
    import logo from "../../images/logo-new.png";
    import navIcon from '../../icons/list-icon.png';
    import setIcon from '../../icons/setting-icon.png';
    import I18n,{ I18nLanguage, I18nCodes, I18nLocale } from '../i18n';
    import Model from './BaseModel';

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
    export default {
        components:{
            "base-drop-down" : DropDown
        },
        props: {
            navClickResolve: {
                type: Function,
                default: function(){},
                required: false
            },
            toFixedOrRelativeResolve: {
                type: Function,
                default: function(){},
                required: false
            },
            setClickResolve:{
                type: Function,
                default: function(){},
                required: false
            },
        },
        data: function () {
            return {
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
                currentTap: '',
                currentTitleClass: {
                    'currentTitle': true,
                    'currentFixed': false
                }
                ,topBarLineStyle: {
                    'height': '80px'
                }
            };
        },
        computed: {
            currentTitle: function(){
                if(this.currentTap){
                    return I18nLocale.getMessage(this.$nav.map[this.currentTap].i18nPath);
                }
            }
        },
        methods: {
            defaultForNavClick: function(){
                this.navClick( this.nav[0] );
            },
            navClick: function( item ){
                this.currentTap = item.id;
                this.$refs.navigation.hideMenu();
                setTimeout( () => {
                    this.navClickResolve( item );
                },1);
            },
            setClick: function( item, groupId ){
                setTimeout(() => {
                    this.setClickResolve( item, groupId );
                },1);
                switch (groupId) {
                    case 'language':
                        I18nLocale.code = item.id;
                        break;
                    default: return;
                }
            },
            toFixedOrRelative: function( scroll ){
                if(!scroll){
                    this.topBar.fixed = !this.topBar.fixed;
                    let flag = this.topBar.fixed;
                    this.logoSrc = flag? undefined: logo;
                    this.setting.navFixed = flag;
                    this.navigation.navFixed = flag;
                    this.currentTitleClass.currentFixed = flag;
                    this.topBarLineStyle['height'] = flag? '50px' : '80px';
                    this.shadowLineStyle['display'] = flag? 'block': 'none';
                    setTimeout( () => {
                        this.toFixedOrRelativeResolve( flag );
                    },1);
                    return true;
                }
                let height = 80 - scroll;
                this.topBarLineStyle['height'] = height + 'px';

            },
            handScroll: function(){
                let flag = this.topBar.fixed;
                let scroll = window.scrollY;
                if(!flag && scroll > 0){
                    if( scroll <= 15){
                        this.toFixedOrRelative( window.scrollY*2 );
                    }else{
                        this.toFixedOrRelative();
                    }
                }else if(flag){
                    if(window.scrollY <= 0){
                        this.toFixedOrRelative();
                    }else if( scroll <= 15 ){
                        this.toFixedOrRelative( window.scrollY*2 );
                    }
                }
            },
            changeTap: function( id ){
                let tap = this.$nav.map[id];
                if(tap){
                    this.currentTap = tap.id;
                    return true;
                }
                return false;
            }

        },
        created: function(){
            let navObj = {
                // "home": 'index.nav.home',
                "news": 'index.nav.news',
                "about": 'index.nav.about',
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

            //添加钩子，当I18nLocale.code发生改变的时候触发
            I18nLocale.addResolve((oldValue, newValue) => {
                this.$nav.refresh();
                setGroup.refresh();
                languageGroup.name = I18nLocale.getMessage('index.sets.language');
            });
        }
    };
</script>
<style scoped>
    .topBar{
        width: 100%;
        height: 80px;
        text-align: center;
        border-bottom: 2px solid #ccc;
        position: relative;
        background: #fff;
        z-index: 999;
    }
    .icon{
        width: 20px;
        height: 20px;
    }
    .logo {
        width: 300px;
    }
    .navigation{
        left: 0;
    }
    .setting{
        right: 0;
    }
    .currentTitle{
        display: inline-block;
        position: absolute;
        bottom: 5px;
        font-weight: bolder;
        left: 10px;
        font-size: 18px;
        color: #676767;
    }
    .fixed{
        position: fixed;
        height: 50px;
        width: calc(100% - 27px);
        top: 0;
    }
    .currentFixed{
        position: relative;
        top: 10px;
    }
    .nav{
        position: absolute;
        top: 20px;
    }
    /* 使用 >>> 来强制修改子模块样式 */
    .nav >>> div > button{
        border: none;
    }
    .navFixed{
        top: 10px;
    }
</style>