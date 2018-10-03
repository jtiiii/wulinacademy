<template>
    <div class="about">
        <nav>
            <a>{{ $t('about.nav.history') }}</a>
            <a>{{ $t('about.nav.contact') }}</a>
            <a>{{ $t('about.nav.map') }}</a>
        </nav>
        <article>
            <img class="promotional" :src="src">
            <br/>
            <div class="introduction">
                <h3>{{ $t('index.title') }}</h3>
                <p>{{ $t('about.contact.phoneNumber') }} {{ phoneNumber }}</p>
                <p>{{ $t('about.contact.email') }} {{ email }}</p>
            </div>
        </article>

    </div>
</template>
<script type="text/javascript">
    import promotionalPic from '../images/photo2.jpeg';
    import i18n from '../scripts/i18n';
    export default {
        i18n,
        data: ()=>{
            return {
                title: '武林书画院',
                src: promotionalPic,
                phoneNumber: '0571-85150209',
                email: 'xxx@xxx.com',
            };
        }
    };
</script>
<style scoped>
    .about{
        text-align : center;
    }
    .promotional{
        margin: 20px;
        width: 600px;
    }
    .introduction{
        width: 600px;
        border-left: 5px solid #ccc;
        text-align: left;
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