<template>
    <div class="manager">
        <image-control class="image-manager"></image-control>
    </div>
</template>
<script type="text/javascript">
    import ImageControl from '../scripts/components/imageControl/ImageControl.vue';
    export default {
        components:{
            'image-control': ImageControl
        },
        data(){
            return {};
        }
    };
</script>
<style scoped>
    .manager{
        display: inline-block;
        text-align: center;
        width: 700px;
    }
    .image-manager{
        width: 100%;
        height: 600px;
    }
</style>