<template>
    <div>
        <div :class="topBar" :style="topBarLineStyle">
            <div :class="navigation" >
                <base-drop-down ref="navigation" :groups="nav.items" :item-click="navClick"><img class="icon" :src="navIcon"/></base-drop-down>
            </div>
            <img v-if="logoSrc" class="logo" :src="logoSrc" />
            <div :class="currentTitleClass" v-if="currentTitle"> {{ currentTitle }} </div>
            <div :class="setting">
                <base-drop-down ref="setting" :menu-direct="'right'" :groups="sets" :item-click="setClick"><img class="icon" :src="settingIcon"/></base-drop-down>
            </div>
        </div>

        <div v-show="loginModal.show" class="login box" :style="loginStyle" v-outsideclick="loginOutSideClick">
            <v-login @loginSuccessful="loginSuccHandle" ></v-login>
        </div>
    </div>
</template>
<script type="text/javascript">
    import DropDown from './BaseDropDown.vue';
    import Login from './Login.vue';

    import logo from "../../images/logo-new.png";
    import navIcon from '../../icons/list-icon.png';
    import setIcon from '../../icons/setting-icon.png';
    import nav from '../navigation';
    import sets,{SetUtils} from '../setting';
    import Utils from '../utils';
    import SecurityService from '../api/SecurityService';



    function initSetting(sets,vue){
        SetUtils.addGroup('login','login.login')
            .addItem('manager', 'manager.managerMode',vue.showLoginClick);
    }


    export default {
        components:{
            "v-login": Login,
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
            }
        },
        data: function () {
            return {
                loginModal:{
                    hideActive: true,
                    show: false,
                    isLogin: false
                },
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
                nav: nav,
                logoSrc: logo,
                navIcon: navIcon,
                settingIcon: setIcon,
                sets: sets,
                currentTap: '',
                currentTitleClass: {
                    'currentTitle': true,
                    'currentFixed': false
                },
                topBarLineStyle: {
                    'height': '80px'
                },
                loginStyle: {
                    'display': 'block',
                }
            };
        },
        directives:{
            outsideclick: Utils.outsideClick
        },
        computed: {
            currentTitle: function(){
                if(this.currentTap){
                    return this.currentTap.text;
                }
                return '';
            }
        },
        methods: {
            defaultForNavClick: function(){
                this.navClick( this.nav.default );
            },
            navClick: function( item ){
                this.$refs.navigation.hideMenu();
                setTimeout( () => {
                    this.navClickResolve( item );
                },1);
            },
            setClick: function( item, groupId ){
                item.resolve( item );
                this.$refs.setting.hideMenu();
                setTimeout(() => {
                    this.setClickResolve( item, groupId );
                },1);
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
                        this.toFixedOrRelative( window.scrollY );
                    }else{
                        this.toFixedOrRelative();
                    }
                }else if(flag){
                    if(window.scrollY <= 0){
                        this.toFixedOrRelative();
                    }else if( scroll <= 15 ){
                        this.toFixedOrRelative( window.scrollY );
                    }
                }
            },
            refreshTapForPath: function( path ){
                this.currentTap = this.nav.map[path];
            },
            showLogin: function(){
                this.loginModal.show = true;
            },
            hideLogin: function(){
                this.loginModal.show = false;
            },
            showLoginClick: function(){
                this.showLogin();
                this.loginModal.hideActive = false;
            },
            loginOutSideClick: function(){
                if(this.loginModal.hideActive){
                    this.hideLogin();
                }else{
                    this.loginModal.hideActive = true;
                }
            },
            loginSuccHandle: function(){
                this.hideLogin();
            }
        },
        mounted(){
            initSetting(sets,this);
        }
    };
</script>
<style scoped>
    @import url("../../styles/box.css");

    .topBar{
        width: 100%;
        height: 80px;
        text-align: center;
        border-bottom: 2px solid #ccc;
        position: absolute;
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
        /*width: calc(100% - 27px);*/
        width: 100%;
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
    .shadow{
        position:relative;
        width: 100%;
        height: 50px;
    }
    .login{
        position: fixed;
        right: 0;
        z-index: 999;
        width: 240px;
        height: 150px;
    }

</style>