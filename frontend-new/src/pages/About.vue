<template>
    <div class="about">
        <article class="content">
            <router-view/>
        </article>
        <v-navigator
                :length="tabs.length"
                :direction="tabDirection"
                :selects="selectTabs"
                @click="changePage"
        >
            <template #item="{ index }">
                {{ $t(tabs[index].i18Key) }}
            </template>
        </v-navigator>
    </div>
</template>
<script type="text/javascript">
    import FComponents from 'f-vue-components';
    import FUtils from 'fo-utils';

    export default {
        components: {
            'v-navigator': FComponents.Navigator
        },
        methods: {
            show: function (tab) {
                this.currentTab = tab.get().id;
            },
            changeTab(index) {
                FUtils.ArrayUtils.clean(this.selectTabs);
                this.selectTabs.push(index);
            },
            changePage(index) {
                let oldIndex = this.selectTabs[0];
                if (oldIndex === index) {
                    return;
                }
                this.changeTab(index);
                this.$router.push(this.tabs[index].path);
            },
        },
        data: ()=> {
            return {
                amap: null,
                markers: {},
                currentTab: 'history',
                tabDirection: 'column',
                mobile: false,
                tabs: [
                    {id: 'history', i18Key: 'about.nav.history', path: '/about/history'},
                    {id: 'contact', i18Key: 'about.nav.contact', path: '/about/contact'},
                    {id: 'map', i18Key: 'about.nav.map', path: '/about/map'}
                ],
                pathTabIndexMap: {},
                selectTabs: [],
            };
        },
        computed:{
            computedTabs(){
                return this.tabs.map( tab => {tab.text = this.$t(tab.i18Key); return tab});
            }
        },
        mounted() {
        },
        created() {
            for (let i = 0; i < this.tabs.length; i++) {
                this.pathTabIndexMap[this.tabs[i].path] = i;
            }
            this.selectTabs.push(this.pathTabIndexMap[this.$route.path]);
            this.$router.afterEach(to => {
                this.changeTab(this.pathTabIndexMap[to.path]);
            });

            if (window.innerWidth <= 850) {
                this.tabDirection = 'row';
            }
        }
    };
</script>
<style>
    @import url("../assets/styles/pages/About.css");
</style>