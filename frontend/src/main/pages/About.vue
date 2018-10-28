<template>
    <div class="about">
        <nav>
            <a>{{ $t('about.nav.history') }}</a>
            <a>{{ $t('about.nav.contact') }}</a>
            <a>{{ $t('about.nav.map') }}</a>
        </nav>
        <article class="content">
            <div class="history">
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
            <div class="contact">
                <h3>{{ $t('index.title') }}</h3>
                <p>{{ $t('about.contact.phoneNumber') }} {{ phoneNumber }}</p>
                <p>{{ $t('about.contact.email') }} {{ email }}</p>
                <p>{{ $t('about.contact.address') }} {{ $t('about.contact.physicalAddress') }}</p>
            </div>
        </article>

    </div>
</template>
<script type="text/javascript">
    import pic2 from '../images/photo2.jpeg';
    import pic3 from '../images/photo3.jpeg';
    import pic4 from '../images/photo4.jpeg';
    import pic5 from '../images/photo5.jpeg';

    import i18n from '../scripts/i18n';
    export default {
        i18n,
        data: ()=>{
            return {
                phoneNumber: '(086) 0571-85150209',
                email: 'wulinacademyarts@gmail.com',
                pics:{
                    pic2: pic3,
                    pic3: pic4,
                    pic4: pic2,
                    pic5: pic5
                }
            };
        }
    };
</script>
<style scoped>
    .about{
        text-align : center;
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
        width: 600px;
        text-align: left;
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
        float: left;
        padding: 10px;
        margin-right: 20px;
    }
    .about > nav > a{
        margin: 10px 0;
        display: block;
    }
    .about > article{
        float: left;
    }
</style>