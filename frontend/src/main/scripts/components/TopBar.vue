<template>
    <div>
        <div class="top-bar" :class="topBarClass" :style="topBarLineStyle">
            <navigator :class="navClass" class="nav-dropdown navigation" />
            <img v-show="logoShow" class="logo" :src="logoSrc" />
            <div class="currentTitle" :class="currentTitleClass" v-if="currentTitle"> {{ currentTitle }} </div>
<!--            <div :class="setting">-->
<!--                <base-drop-down ref="setting" :menu-direct="'right'" :groups="sets" :item-click="setClick"><img class="icon" :src="settingIcon"/></base-drop-down>-->
<!--            </div>-->
            <setting :class="navClass" class="nav-dropdown setting" />
        </div>

        <div v-show="showLoginBox" class="login box" :style="loginStyle" v-outsideclick="loginOutSideClick">
            <v-login @loginSuccessful="loginSuccHandle" ></v-login>
        </div>
    </div>
</template>
<script type="text/javascript">
    import DropDown from './BaseDropDown.vue';
    import Login from './Login.vue';

    import logo from "../../images/logo-new.png";
    import sets,{SetUtils} from '../setting';
    import Utils from '../utils';

    import {mapPage} from "../pages";

    import Navigator from './navigator/navigator.vue';
    import Setting from './setting/setting.vue';



    function initSetting(sets,vue){
        SetUtils.addGroup('login','login.login')
            .addItem('manager', 'manager.managerMode',vue.showLoginClick);
    }


    export default {
        components:{
            "v-login": Login,
            "base-drop-down" : DropDown,
            "navigator": Navigator,
            "setting": Setting
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
            scroll:{
                type: Number,
                required: false,
                default: 0
            }
        },
        data: function () {
            return {
                position:{
                    fixed: false,
                },
                navigator:{
                    show: false,
                },
                loginModal:{
                    hideActive: true,
                    show: false,
                    isLogin: false
                },
                logoSrc: logo,
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
                if(this.$route.name){
                    return this.$t(mapPage[this.$route.name].i18key);
                }
                return '';
            },
            topBarClass(){
                return {'top-bar-fixed' : this.position.fixed }
            },
            navClass(){
                return { 'navFixed': this.position.fixed };
            },
            logoShow(){
                return !this.position.fixed;
            },
            currentTitleClass(){
                return {'currentFixed': this.position.fixed};
            },
            showLoginBox:{
                set( show ){
                    this.$store.commit('setShowLoginBox', show);
                },
                get(){
                    return this.$store.state.showLoginBox;
                }
            }
        },
        watch: {
            scroll(){
                this.handScroll();
            }
        },
        methods: {
            toFixedOrRelative: function( scroll ){
                if(!scroll){
                    this.position.fixed = !this.position.fixed;
                    this.topBarLineStyle['height'] = this.position.fixed ? '50px' : '80px';
                    setTimeout( () => {
                        this.toFixedOrRelativeResolve( this.position.fixed );
                    },1);
                    return true;
                }
                let height = 80 - scroll;
                this.topBarLineStyle['height'] = height + 'px';

            },
            handScroll: function(){
                let flag = this.position.fixed;
                if(!flag && this.scroll > 0){
                    if( this.scroll <= 15){
                        this.toFixedOrRelative( this.scroll );
                    }else{
                        this.toFixedOrRelative();
                    }
                }else if(flag){
                    if(this.scroll <= 0){
                        this.toFixedOrRelative();
                    }else if( scroll <= 15 ){
                        this.toFixedOrRelative( this.scroll );
                    }
                }
            },
            showLogin: function(){
                this.loginModal.show = true;
            },
            hideLogin: function(){
                this.showLoginBox = false;
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

    .top-bar{
        width: 100%;
        height: 80px;
        text-align: center;
        border-bottom: 2px solid #ccc;
        position: absolute;
        background: #fff;
        z-index: 999;
        display: flex;
        flex-flow: column nowrap;
        align-items: center;
        justify-content: center;
    }
    .top-bar-fixed{
        position: fixed;
        height: 3.125rem; /* 50px */
        width: 100%;
        top: 0;
    }
    .currentTitle{
        display: inline-block;
        position: absolute;
        bottom: 0.25rem; /* 4px */
        font-weight: bolder;
        left: 1rem; /* 48px */
        font-size: 18px;
        color: #676767;
    }
    @media screen and (max-width:480px){
        .logo {
            position: relative;
            width: 13rem; /* 208px */
        }
    }
    @media screen and (min-width:480px){
        .logo {
            width: 300px;
        }
    }
    .nav-dropdown{
        position: absolute;
        top: 1.5rem; /* 24px */
        z-index: 2;
    }
    .navigation{
        left: 0.5rem; /* 8px */
    }
    .setting{
        right: 0.5rem; /* 8px */
    }
    .currentFixed{
        position: relative;
        left: 0;
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