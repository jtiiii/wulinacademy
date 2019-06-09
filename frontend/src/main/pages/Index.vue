<template>
    <div class="body" :style="fontStyle">
        <v-top-bar class="top" :scroll="scroll" ref="topBar" :nav-click-resolve="changePage" :to-fixed-or-relative-resolve="fixTopBar" ></v-top-bar>
        <v-content class="content" ref="content"></v-content>
        <v-footer class="footer" ref="footer"></v-footer>
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
        router,
        data(){
            return {
                fontStyle: {
                    'font-family': fontFamily.es
                },
                scroll: 0
            };
        },
        computed: {
            Content: function(){
                return this.$refs['content'].$children[0];
            }
        },
        methods: {
            changePage: function( item ){
                if(item.defaultValue){
                    router.push({
                        name:item.name,
                        params:item.defaultValue
                    });
                }else{
                    router.push({name:item.name});
                }
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
            let preProcessFontFamily = ()=>{
                I18nLocale.addResolve( (old, value) => {
                    this.changeFontFamily(value);
                });
            };
            let preProcessEventListeners = ()=>{
                this.$el.addEventListener("scroll",() => {
                    this.scroll = this.$el.scrollTop;
                } );
            };
            //预处理序列
            new Promise( resolve => resolve() )
                // .then( preProcessRouter )
                .then( preProcessFontFamily )
                .then( preProcessEventListeners );
        }
    }
</script>
<style>
    @import "../fonts/fonts.css";
    @import "../styles/index.css";
</style>
<style scoped>
    .body{
        height: 100%;
        overflow: hidden;
        overflow-y: scroll;
        position: relative;
    }
    .top{
        /*position: fixed;*/
    }
    .content{
        position: relative;
        /*height: 100%;*/
        top: 100px;
        /*overflow: hidden;*/
        /*overflow-y: scroll;*/
    }
    .footer{
        top: 100px;
        position: relative;
        /*bottom: -185px;*/
    }
</style>