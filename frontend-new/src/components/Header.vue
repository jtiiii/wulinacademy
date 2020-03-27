<template>
    <div class="header">
        <div v-show="fixed" class="placeholder-box">
        </div>
        <div class="box" :class="{fixed:fixed}">
        <div  class="content-box">
            <div class="menu">
                <v-selector
                        :selects="menu.selects"
                        :length="pages.length"
                        :size="'small'"
                        :panel-class="['menu-selector-panel']"
                        :listShow="menu.listShow"
                        @openOrClose="openOrClose"
                        @itemClick="select"
                        v-outsideClick="closeMenu"
                >
                    <a class="btn iconfont wulin-menu" @click="menu.listShow = true" />
                    <template #item="{index}">{{ $t(pages[index].i18key) }}</template>
                </v-selector>
            </div>
            <div class="logo">
                <div>
                    <img :alt="$t(currentTab.i18key)" :src="fixed? smallLogSrc: logoSrc">
                </div>
                <span v-if="currentTab" v-show="fixed">{{ $t(currentTab.i18key) }}</span>
            </div>
            <div class="setting">
                <v-dropdown v-model="setting.listShow"
                            :panel-class="['setting-dropdown-panel']"
                >
                    <template #button>
                        <a class="btn iconfont wulin-setting" @click="setting.listShow = true" />
                    </template>
                    <template>
                        <div class="group" v-for="group in setting.groups" :key="group.id">
                            <div class="title">
                                <hr/><span>{{ $t(group.i18Key) }}</span>
                            </div>
                            <v-navigator :length="group.items.length" :direction="'column'" :selects="group.selects" @click="index => settingClick(group,index)">
                                <template #item="{index}">
                                    <span>{{ group.items[index].i18Key? $t(group.items[index].i18Key): group.items[index].text }}</span>
                                </template>
                            </v-navigator>
                        </div>
                    </template>
                </v-dropdown>
            </div>
            <v-dialog class="login-modal" :canClose="true" :size="'medium'" :show="login.show" :position="'outside-bottom'" @close="login.show = false">
                <template #title>
                    {{$t('login.login')}}
                </template>
                <v-login @loginSuccessful="login.show = false" />
            </v-dialog>
        </div>
        </div>
    </div>
</template>

<script>
    import FComponents from 'f-vue-components';
    import logoSrc from '../assets/images/logo-03.png';
    import smallLogSrc from '../assets/images/header-logo-small.jpg';
    import {Pages} from "../pages/pages";
    import Login from '../components/Login.vue';
    import VueUtils from 'f-vue-components/src/scripts/util/VueUtils';
    const pages = Pages.filter( p => !p.hidden);
    console.info(pages);
    const pageMap = {};
    Pages.forEach( p => {
        pageMap[p.path] = p;
    });
    const languageGroup = { id: 'language', i18Key: 'index.sets.language', selects:[],
            items:[
                { id: 'zh-CN', text: '简体中文'},
                { id: 'zh-TW', text: '正體中文'},
                { id: 'en-US', text: 'English'}
            ]
        };
    export default {
        name: "Header",
        components:{
            'v-selector': FComponents.Selector,
            'v-dropdown': FComponents.Dropdown,
            'v-navigator': FComponents.Navigator,
            'v-dialog': FComponents.Dialog,
            'v-login': Login
        },
        data(){
            return {
                logoSrc: logoSrc,
                smallLogSrc: smallLogSrc,
                menu:{
                    selects: [],
                    listShow: false
                },
                setting:{
                    groups:[
                        languageGroup,
                        { id: 'manager', i18Key: 'manager.managerMode', selects:[],
                            items: [
                                { id: 'login', i18Key: 'login.login'}
                            ]
                        }
                    ],
                    selects: [],
                    listShow: false
                },
                login:{
                    show: false
                },
                fixed: false,
                pages: pages,
                pageMap: pageMap,
                length: 3,
            };
        },
        directives:{
            outsideClick: VueUtils.directives.outsideClick
        },
        computed:{
            currentTab(){
                if(this.pageMap[this.$route.path]){
                    return this.pageMap[this.$route.path]
                }
                return '';
            }
        },
        methods:{
            openOrClose( isOpen ){
                this.menu.listShow = isOpen;
            },
            select( index ){
                if(this.$route.path !== this.pages[index].path){
                    this.$router.push(this.pages[index].path);
                }
                this.closeMenu();
            },
            closeMenu(){
                this.menu.listShow = false;
            },
            settingClick( group,index ){
                switch (group.id) {
                    case 'language': this.changeLocale(index);break;
                    case 'manager':
                        this.managerClick(group.items[index].id);
                        break;
                    default: break;
                }
            },
            changeLocale(index){
                let selects = languageGroup.selects;
                if(!selects.includes(index)){
                    selects.pop();
                    selects.push(index);
                    this.$i18n.locale = languageGroup.items[index].id;
                }
                this.setting.listShow = false;
            },
            managerClick( id ){
                switch (id) {
                    case 'login':
                        this.login.show = true;
                        break;
                    default: break;
                }
            },
            getLocaleIndex( locale ){
                let index = null;
                languageGroup.items.forEach( (item, i) => {
                    if(item.id === locale){
                        index = i;
                    }
                });
                return index;
            }
        },
        mounted(){
            window.addEventListener('scroll', () => {
                this.fixed = window.scrollY >= 80;
            });
        },
        created(){
            let locale = this.$store.state.locale;
            let index = this.getLocaleIndex( locale);
            if(!index && index !== 0) {
                index = this.getLocaleIndex( 'en-US');
            }
            console.log(index);
            this.changeLocale(index);
        }
    }
</script>
<style lang="less">
    @import url('../assets/styles/components/header.less');
</style>