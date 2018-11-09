<template>
    <div class="footer">
        <p>{{ $t('index.footer.p1') }}</p>
        <p>{{ $t('index.footer.p2') }}</p>
        <p>{{ $t('index.footer.p3') }}</p>
        <p>310000</p>
        <p style="margin: 10px 0;font-size:12px;">&copy; {{ $t('index.footer.copy') }}</p>
        <div style="height:29px;background-color:rgb(169,161,154);">
            <img style="position:relative; top:3px; width: 120px;" :src="footerLogo">
        </div>
    </div>
</template>
<script type="text/javascript">
    import footerLogo from '../../images/footer-logo.jpg';
    import I18n from '../i18n';
    export default {
        i18n: I18n,
        data: function(){
            return {
                footerLogo: footerLogo
            };
        }
    };
</script>
<style scoped>
    .footer{
        margin-top: 20px;
        padding-top: 10px;
        width: 100%;
        font-size: 16px;
        text-align: center;
        border-top: 2px solid #ccc;
        position: relative;
        background: #fff;
        color: rgb(113,113,113);
    }
    .footer > p{
        margin: 5px auto;
    }
</style>