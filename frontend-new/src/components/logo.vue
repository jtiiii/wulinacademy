<template>
    <a class="logo" :title="$t(logo.i18key)" :href="logo.url" @click="click">
        <i class="iconfont" :class="iconLogo"></i>
<!--        <svg class="icon" aria-hidden="true">-->
<!--            <i class="iconfont" :class="iconLogo"></i>-->
<!--            <use :xlink:href="logo.icon"></use>-->
<!--        </svg>-->
    </a>
</template>
<script type="text/javascript">
    export default {
        props:{
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
            iconLogo(){
                let result = {};
                result[this.logo.icon] = true;
                console.info(result);
                return result;
            }
        }
    }
</script>

<style scoped>
    .logo{
        width: 5rem;
        height: 5rem;
        display: inline-block;
        margin: 15px;
    }
    .logo > .iconfont{
        font-size: 5rem;
        color: #ccc;
        border: none;
        text-underline: none;
        background: none;

    }
</style>