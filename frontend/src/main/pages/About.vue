<template>
    <div class="about" :style="aboutStyle">
        <nav>
            <a @click="goto('history')">{{ $t('about.nav.history') }}</a>
            <a @click="goto('contact')">{{ $t('about.nav.contact') }}</a>
            <a @click="goto('map')">{{ $t('about.nav.map') }}</a>
        </nav>
<!--        <article class="content" :style="contentHeight">-->
        <article class="content">
            <div ref="history" class="history">
                <p>{{ $t('about.history.h1') }}</p>
                <p>{{ $t('about.history.h2') }}</p>
                <p>{{ $t('about.history.h3') }}</p>
                <br/>
                <ul class="briefHistory">
                    <li>
                        <img class="pic" :src="pics.pic2">
                        <div class="picDescription">{{ $t('about.history.t1') }}</div>
                    </li>
                    <li>
                        <img class="pic" :src="pics.pic3">
                        <div class="picDescription">{{ $t('about.history.t2') }}</div>
                    </li>
                    <li>
                        <img class="pic" :src="pics.pic4">
                        <div class="picDescription">{{ $t('about.history.t3') }}</div>
                    </li>
                    <li>
                        <img class="pic" :src="pics.pic5">
                        <div class="picDescription">{{ $t('about.history.t4') }}</div>
                    </li>
                </ul>
            </div>
            <br/>
            <div ref="contact" class="contact">
                <h3>{{ $t('index.title') }}</h3>
                <p><b>{{ $t('about.contact.phoneNumber') }}</b> {{ phoneNumber }}</p>
                <p><b>{{ $t('about.contact.email') }}</b> {{ email }}</p>
                <p><b>{{ $t('about.contact.address') }}</b> {{ $t('about.contact.physicalAddress') }}</p>
            </div>
            <div class="dituAmap" ref="map">
                <div id="mapContainer">

                </div>
            </div>
        </article>

    </div>
</template>
<script type="text/javascript">
    import pic2 from '../images/photo6.jpeg';
    import pic3 from '../images/photo3.jpeg';
    import pic4 from '../images/photo4.jpeg';
    import pic5 from '../images/photo5.jpeg';

    import i18n from '../scripts/i18n';
    import Map from '../scripts/amap/Map';

    export default {
        i18n,
        methods: {
            goto: function( part ){
                this.$refs[part].scrollIntoView();
            },
            resize: function(){
                this.aboutStyle.height = window.innerHeight - 306 + 'px';
            }
        },
        data: ()=> {
            return {
                phoneNumber: '(086) 0571-85150209',
                email: 'wulinacademyarts@gmail.com',
                pics:{
                    pic2: pic3,
                    pic3: pic4,
                    pic4: pic2,
                    pic5: pic5
                },
                // Amap: null,
                amap: null,
                aboutStyle: {
                    'height': '0px'
                }
            };
        },
        computed: {
            // contentHeight: function(){
            //     return {
            //         'height': window.innerHeight + 'px'
            //     };
            // },
            // aboutStyle: function(){
            //     return {
            //         'height': window.innerHeight - 306 + 'px'
            //     };
            // }
        },
        mounted: function(){
            Map.loadScript().then( amap => {
                // this.Amap = amap;
                this.amap = new Map('mapContainer');
                this.amap.marker({longitude: 120.161118, latitude: 30.246205, title: 'home'});
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
                });
            });
        },
        created(){
            let _vue = this;
            this.resize();
            window.onresize = function (){
                _vue.resize();
            }
        }
    };
</script>
<style scoped>
    .picDescription{
        font-size: 13px;
    }
    .about{
        text-align : center;
        height: 100%;
        position: relative;
        display: inline-block;
    }
    .briefHistory{
        list-style-type: none;
        padding: 0;
        margin: 0;
    }
    .briefHistory > li{
        margin: 20px 0;
    }
    .pic{
        width: 600px;
    }
    .picDescription{
        text-align: center;
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
        /*float: left;*/
        padding: 10px;
        margin-right: 20px;
        width: 120px;
        position: absolute;
        left: -100px;
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
    .about > article{
        float: left;
        height: 100%;
        overflow: hidden;
        overflow-y: scroll;
    }

    .dituAmap{
        margin: 20px auto;
    }
    #mapContainer{
        height: 500px
    }
</style>