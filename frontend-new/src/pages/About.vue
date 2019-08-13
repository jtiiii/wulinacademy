<template>
    <div class="about">
        <navigator :tabs="computedTabs" @a-click="show"></navigator>
        <article class="content">
            <history v-show="currentTab === 'history'"></history>
            <div v-show="currentTab === 'contact'" class="contact">
                <h3>{{ $t('index.title') }}</h3>
                <p><b>{{ $t('about.contact.phoneNumber') }}</b> {{ phoneNumber }}</p>
                <p><b>{{ $t('about.contact.email') }}</b> {{ email }}</p>
                <p><b>{{ $t('about.contact.address') }}</b> {{ $t('about.contact.physicalAddress') }}</p>
            </div>
            <div v-show="currentTab === 'map'" class="dituAmap">
                <div id="mapContainer">
                </div>
            </div>
        </article>
    </div>
</template>
<script type="text/javascript">
    // import Map from '../scripts/amap/Map';
    import FComponents from 'f-vue-components';

    const History = resolve => require(['../scripts/components/History.vue'],resolve);
    const test = FComponents.Layout.Navigator;
    console.info(test);
    export default {
        components:{
            'history': History,
            'navigator': FComponents.Layout.Navigator
        },
        methods: {
            show: function( tab ){
                this.currentTab = tab.get().id;
            },
            resize(){
                if(window.innerWidth >= 900){
                    this.mobile = false;
                }else{
                    this.mobile = true;
                }
            },
        },
        data: ()=> {
            return {
                phoneNumber: '(086) 0571-85150209',
                email: 'wulinacademyarts@gmail.com',
                amap: null,
                markers:{},
                currentTab: 'history',
                mobile: false,
                tabs:[
                    {id:'history', i18Key: 'about.nav.history',text: '' },
                    {id:'contact', i18Key: 'about.nav.contact',text:''},
                    {id:'map', i18Key: 'about.nav.map',text:''}
                ]
            };
        },
        computed:{
            computedTabs(){
                return this.tabs.map( tab => {tab.text = this.$t(tab.i18Key); return tab});
            }
        },
        mounted: function(){
            Map.loadScript().then( amap => {
                // this.Amap = amap;
                this.amap = new Map('mapContainer');


                this.markers['home'] = this.amap.marker({longitude: 120.161118, latitude: 30.246205, title: 'home'});
                // this.amap.addZoomControl();
                let map = this.amap.map;
                Map.loadUIScript().then( () => {
                    AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {
                        //添加一个缩放控件
                        map.addControl(new BasicControl.Zoom({
                            position: 'lt'
                        }));

                        //缩放控件，显示Zoom值
                        map.addControl(new BasicControl.Zoom({
                            position: 'lb',
                            showZoomNum: true
                        }));

                        //图层切换控件
                        map.addControl(new BasicControl.LayerSwitcher({
                            position: 'rt'
                        }));
                    });
                    this.amap.setFitView();
                });
            });
        },
        created(){
            let _vue = this;
            this.resize();
            // Utils.WindowUtils.onresize( () => {
            //     _vue.resize();
            // });
        }
    };
</script>
<style scoped>
    .about{
        text-align : center;
        /*height: 100%;*/
        position: relative;
        display: inline-block;
    }
    .content{
        padding: 0 15px;
        width: 600px;
    }
    .contact{
        width: 100%;
        border-left: 5px solid #ccc;
        padding: 5px 15px;
        display: inline-block;
        box-sizing: border-box;
        background: rgb(243, 243, 243);
        color: rgba(160, 160, 160, 1);
    }
    .about > nav{
        text-align: left;
        position: fixed;
        left: 2.5rem /* 40px */;
        width: 7rem; /* 112px */
    }
    .about > nav > a{
        font-size: 17px;
        color: #444;
        margin: 10px 0;
        display: block;
        cursor: pointer;
    }
    .about > nav > a:hover{
        opacity: .7;
    }
    /*.about > article{*/
    /*    float: left;*/
    /*    height: 100%;*/
    /*    overflow: hidden;*/
    /*    overflow-y: scroll;*/
    /*}*/

    .dituAmap{
        margin: 20px auto;
    }
    #mapContainer{
        height: 500px
    }
</style>