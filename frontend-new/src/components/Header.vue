<template>
    <div class="header">
        <div class="menu">
            <v-selector
                    :selects="menu.selects"
                    :length="pages.length"
                    :size="'small'"
                    :listShow="menu.listShow"
                    @openOrClose="openOrClose"
                    @itemClick="select"
                    v-outsideClick="closeMenu"
                >
                <a class="btn" @click="menu.listShow = true"><i class="iconfont wulin-menu"></i></a>
                <template #item="{index}">{{ $t(pages[index].i18key) }}</template>
            </v-selector>
        </div>
        <div class="logo"><img :src="logoSrc"></div>
        <div class="setting">
            <v-dropdown v-model="setting.listShow">
                <template #button>
                    <a class="btn" @click="setting.listShow = true"><i class="iconfont wulin-setting"></i></a>
                </template>
                <template>
                    <div class="group" v-for="group in setting.groups" :key="group.id">
                        <div class="title">
                            <hr/><span>{{ $t(group.i18Key) }}</span>
                        </div>
                        <v-navigator :length="group.items.length" :direction="'column'" :selects="group.selects" @click="index => settingClick(group,index)">
                            <template #item="{index}">
                                <span >{{ group.items[index].i18Key? $t(group.items[index].i18Key): group.items[index].text }}</span>
                            </template>
                        </v-navigator>
                    </div>
                </template>
            </v-dropdown>
        </div>
        <v-modal class="login-modal" :canClose="true" style="position: absolute" :size="'medium'" :show="login.show" :position="'outside-bottom'" @close="login.show = false">
            <template #title>
                {{$t('login.login')}}
            </template>
            <v-login @loginSuccessful="login.show = false"></v-login>
        </v-modal>
    </div>
</template>

<script>
    import FComponents from 'f-vue-components';
    import logoSrc from '../assets/images/logo-03.png';
    import {Pages} from "../pages/pages";
    import Login from '../components/Login.vue';
    import VueUtils from 'f-vue-components/src/scripts/util/VueUtils';
    const pages  = Pages.filter( p => !p.hidden);
    export default {
        name: "Header",
        components:{
            'v-selector': FComponents.Selector,
            'v-dropdown': FComponents.Dropdown,
            'v-navigator': FComponents.Navigator,
            'v-modal': FComponents.Modal,
            'v-login': Login
        },
        data(){
            return {
                logoSrc: logoSrc,
                menu:{
                    selects: [],
                    listShow: false
                },
                setting:{
                    groups:[
                        { id: 'language', i18Key: 'index.sets.language', selects:[],
                            items:[
                                { id: 'zh-CN', text: '简体中文'},
                                { id: 'zh-TW', text: '正體中文'},
                                { id: 'en-US', text: 'English'}
                            ]
                        },
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
                pages: pages,
                length: 3,
            };
        },
        directives:{
            outsideClick: VueUtils.directives.outsideClick
        },
        computed:{

        },
        methods:{
            openOrClose( isOpen ){
                this.listShow = isOpen;
            },
            select( index ){
                console.info(this.pages[index]);
                this.$router.push(this.pages[index].path);
                // console.info(index);
            },
            closeMenu(){
                this.menu.listShow = false;
            },
            settingClick( group,index ){
                console.log('click', group);
                switch (group.id) {
                    case 'language': this.changeLocale(group, group.items[index], index);break;
                    case 'manager':
                        this.managerClick(group.items[index].id);
                        break;
                    default: break;
                }
            },
            settingClickItem( group){
                console.info('item-click', group);
                return this.settingClick.bind(this,[group]);
            },
            changeLocale(group, item ,index){
                if(!group.selects.includes(index)){
                    group.selects.pop();
                    group.selects.push(index);
                    this.$i18n.locale = item.id;
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

        }
    }
</script>
<style lang="less">
    @import url('../assets/styles/components/header.less');
</style>