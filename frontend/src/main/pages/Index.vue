<template>
    <div :style="fontStyle">
        <v-top-bar ref="topBar" :nav-click-resolve="changePage" :to-fixed-or-relative-resolve="fixTopBar" ></v-top-bar>
        <v-content ref="content"></v-content>
        <v-footer ref="footer"></v-footer>
    </div>
</template>
<script type="text/javascript">
    import { I18nLocale } from '../scripts/i18n';
    import router from '../scripts/pages';

    import ContentPage from '../scripts/components/ContentPage.vue';
    import TopBar from '../scripts/components/TopBar.vue';
    import Footer from '../scripts/components/Footer.vue';

    const fontFamily = {
        en: 'Myriad Pro',
        //华文细黑
        zh: 'STHeiti'
    };

    export default {
        components: {
            "v-top-bar": TopBar,
            "v-content": ContentPage,
            "v-footer": Footer
        },
        data(){
            return {
                fontStyle: {
                    'font-family': fontFamily.es
                }
            };
        },
        computed: {
            Content: function(){
                return this.$refs['content'].$children[0];
            }
        },
        methods: {
            changePage: function( item ){
                router.push(item.path);
            },
            fixTopBar: function( flag ){
                // console.info(flag);
            },
            changeFontFamily: function( locale ){
                let code = locale.substring(0,locale.indexOf("-"));
                switch (code){
                    case 'zh': this.fontStyle["font-family"] = fontFamily.zh; break;
                    case 'en': this.fontStyle["font-family"] = fontFamily.en; break;
                    default: this.fontStyle["font-family"] = fontFamily.en;
                }
            },
        },
        mounted: function(){
            let preProcessRouter = ()=>{
                router.afterEach( to => {
                    this.$refs.topBar.refreshTapForPath(to.path);
                    if(to.path === '/'){
                        this.$refs.topBar.defaultForNavClick();
                    }
                });
            };
            let preProcessFontFamily = ()=>{
                I18nLocale.addResolve( (old, value) => {
                    this.changeFontFamily(value);
                });
            };
            let preProcessEventListeners = ()=>{
                document.addEventListener("scroll",this.$refs.topBar.handScroll );
            };
            //预处理序列
            new Promise( resolve => resolve() )
                .then( preProcessRouter )
                .then( preProcessFontFamily )
                .then( preProcessEventListeners );
        }
    }
</script>
<style>
    @import "../fonts/fonts.css";
</style>