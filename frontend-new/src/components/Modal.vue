<template>
    <div class="modal" :class="classes.modal" :status="modalStatus">
        <div class="shadow" ></div>
        <dialog open>
            <slot></slot>
        </dialog>
    </div>
</template>
<script type="text/javascript">
    export default {
        props: {
            show:{
                type: Boolean,
                required: false,
                default: true,
            }
        },
        data: function(){
            return {
                open: true,
                classes:{
                    modal:{
                        'hidden': true,
                        'show': false,
                        'hide': false
                    }
                }
            };
        },
        computed: {
            modalStatus: function(){
                this.showAction(this.show);
                return this.show;
            }
        },
        methods: {
            showAction: function( flag ){
                this.classes.modal['show'] = flag;
                this.classes.modal['hide'] = !flag;
                setTimeout(()=>{
                    this.classes.modal['hidden'] = !flag;
                },180);
            }
        }
    }
</script>
<style lang="less" scoped>
    @import url('../assets/styles/modal.less');
</style>