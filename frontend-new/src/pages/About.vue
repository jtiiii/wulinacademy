<template>
    <div class="about">
        <article class="content">
            <div class="history">
                <p>{{ $t('about.history.h1') }}</p>
                <p>{{ $t('about.history.h2') }}</p>
                <p>{{ $t('about.history.h3')}}</p>
                <div class="img">
                    <img :src="pics[1]"/>
                    <span>{{ $t('about.history.t1') }}</span>
                </div>
                <div class="img">
                    <img :src="pics[2]"/>
                    <span>{{ $t('about.history.t2') }}</span>
                </div>
                <div class="img">
                    <img :src="pics[4]"/>
                    <span>{{ $t('about.history.t3') }}</span>
                </div>
                <div class="img">
                    <img :src="pics[3]"/>
                    <span>{{ $t('about.history.t4') }}</span>
                </div>
            </div>
            <div class="contact">
                <h2>{{ $t('index.title') }}</h2>
                <p><b>{{ $t('about.contact.phoneNumber') }}</b><span>{{ phoneNumber }}</span></p>
                <p><b>{{ $t('about.contact.email') }}</b><a :href="'mailto:' + email">{{ email }}</a></p>
                <p><b>{{ $t('about.contact.address') }}</b><span>{{ $t('about.contact.physicalAddress') }}</span></p>
            </div>
            <div v-show="currentTab === 'map'" class="dituAmap">
                <div id="mapContainer">
                </div>
            </div>
        </article>
    </div>
</template>
<script type="text/javascript">
    import pic1 from '../assets/images/about-pic/photo2.jpg';
    import pic2 from '../assets/images/about-pic/photo3.jpg';
    import pic3 from '../assets/images/about-pic/photo4.jpg';
    import pic4 from '../assets/images/about-pic/photo5.jpg';
    import pic5 from '../assets/images/about-pic/photo6.jpg';

    export default {
        components:{
        },
        methods: {
            show: function( tab ){
                this.currentTab = tab.get().id;
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
                ],
                pics:[
                    pic1,pic2,pic3,pic4,pic5
                ]
            };
        },
        computed:{
            computedTabs(){
                return this.tabs.map( tab => {tab.text = this.$t(tab.i18Key); return tab});
            }
        },
        mounted: function(){

        },
        created(){
        }
    };
</script>
<style>
    @import url("../assets/styles/pages/About.css");
</style>