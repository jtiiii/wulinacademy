<template>
    <a class="logo" :style="logoStyle" :title="$t(logo.i18key)" :href="logo.url" @click="click">
<!--        <img :title="logo.title" :src="logo.src"/>-->
        <svg class="icon" aria-hidden="true">
            <use :xlink:href="logo.icon"></use>
        </svg>
    </a>
</template>
<script type="text/javascript">
    import '../font-symbol';
    import i18n from '../i18n';
    export default {
        i18n,
        props:{
            width: {
                type: String,
                required: false,
                default: '100px'
            },
            height: {
                type: String,
                required: false,
                default: '100px'
            },
            logo: {
                type: Object,
                required: true,
            }
        },
        methods: {
            click: function(){
                this.$emit('click', this.logo);
            }
        },
        computed: {
            logoStyle: function(){
                return {
                    'width': this.width,
                    'height': this.height
                }
            }
        }
    }
</script>

<style scoped>
    .logo{
        width: 100px;
        height: 100px;
        display: inline-block;
        margin: 15px;
    }
    .logo > svg{
        height: 100%;
        width: 100%;

    }
    .icon {
        /*width: 1em; height: 1em;*/
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;

    }
</style>