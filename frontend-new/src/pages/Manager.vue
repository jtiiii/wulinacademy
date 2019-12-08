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
        display: flex;
        flex-flow: column nowrap;
        text-align: center;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
</style>