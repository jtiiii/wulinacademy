<template>
    <div class="topbar">
        <dropDown ref="navigation" class="navigation" :groups="nav" :item-click="navClick"><img class="icon" :src="navIcon"/></dropDown>
        <img v-if="logoSrc" class="logo" :src="logoSrc" />
        <div class="currentTitle" v-if="currentTitle"> {{ currentTitle }} </div>
        <dropDown ref="setting" class="setting" :menu-direct="'right'" :groups="sets" :item-click="setClick"><img class="icon" :src="settingIcon"/></dropDown>
    </div>
</template>
<script type="text/javascript">
    import DropDown from './BaseDropDown.vue';
    import navIcon from '../../icons/list-icon.png';
    import settingIcon from '../../icons/setting-icon.png';
    export default {
        name: "top-bar",
        components: {
            "dropDown": DropDown
        },
        props:{
            nav: Array,
            sets: Array,
            navClick: Function,
            setClick: Function,
            logoSrc: {
                type:String,
                required: false,
                default: undefined
            },
            currentTitle: {
                type: String,
                required: false,
                default: undefined
            }
        },
        data: ()=> {
            return {
                navIcon: navIcon,
                settingIcon: settingIcon
            }
        }
    };
</script>
<style scoped>
    .topbar{
        width: 100%;
        height: 80px;
        text-align: center;
        border-bottom: 2px solid #ccc;
        position: relative;
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
        position: absolute;
        top: 20px;
    }
    .setting{
        right: 0;
        position: absolute;
        top: 20px;
    }
    .currentTitle{
        display: inline-block;
        position: absolute;
        bottom: 5px;
        font-weight: bolder;
        left: 10px;
        font-size: 24px;
    }
</style>
<style>
    .setting > button , .navigation > button{
        border: none;
    }
</style>