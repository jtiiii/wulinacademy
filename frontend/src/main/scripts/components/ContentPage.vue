<template>
    <div class="content">
        <article>
            <router-view></router-view>
        </article>
    </div>
</template>
<script type="text/javascript">
    import router from '../router';
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
    }
    .content > article{
        display: inline-block;
    }
    .contentFixed{
        padding-top: 50px;
    }

</style>