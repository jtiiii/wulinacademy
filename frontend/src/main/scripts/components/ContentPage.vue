<template>
    <div class="content">
        <router-view ref="page"></router-view>
    </div>
</template>
<script type="text/javascript">
    import router from '../pages';
    export default {
        router,
        data: function(){
            return {
                content: {
                    'content': true,
                    'contentFixed': false
                }
            };
        }
    };
</script>
<style scoped>
    .content{
        text-align: center;
        width: 100%;
    }
    .content > article{
        display: inline-block;
    }
    .contentFixed{
        padding-top: 50px;
    }

</style>