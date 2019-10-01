<template>
    <div class="manager">
        <image-control @init="imageControlLoad" class="image-manager"></image-control>
    </div>
</template>
<script type="text/javascript">
    import ImageControl from '../components/ImageControl.vue';

    export default {
        components:{
            'image-control': ImageControl
        },
        methods:{
            imageControlLoad: function( load ){
                this.init.imageControl = load;
            }
        },
        data(){
            return {
                init: {
                    imageControl: null
                }
            };
        },
        mounted(){
            this.init.imageControl();
        },
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