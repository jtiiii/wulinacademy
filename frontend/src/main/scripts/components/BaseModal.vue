<template>
    <div class="modal" :class="classes.modal" :status="modalStatus">
        <div class="shadow" ></div>
        <dialog open :style="dialogSize">
            <slot></slot>
        </dialog>
    </div>
</template>
<script type="text/javascript">
    export default {
        props: {
            width: {
                type: Number,
                required: false
            },
            height: {
                type: Number,
                required: false
            },
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
            dialogSize: function(){
                let result = {};
                if(this.width){
                    result['width'] = this.width + 'px';
                }
                if(this.height){
                    result['height'] = this.height + 'px';
                }
                return result;
            },
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
<style type="text/css" scoped>
    .modal{
        width: 100%;
        height: 100%;
        text-align: center;
        z-index: 999;
        position: fixed;
        overflow: scroll;
    }
    .hidden{
        display: none;
    }
    .show{
        animation: show-smooth 0.2s;
    }
    .hide{
        animation: hide-smooth 0.2s;
    }
    dialog{
        display: inline-block;
        height: auto;
        border: none;
        border-radius: 5px;
        margin: 30px auto;
        position: relative;
        z-index: 999;
        background: #fff;
    }
    .shadow{
        position: fixed;
        z-index: 998;
        width: 100%;
        height: 100%;
        background: rgba(119, 119, 119, 0.16);
    }

    @keyframes show-smooth {
        from{
            opacity: 0;
        }
        to{
            opacity: 1;
        }
    }

    @keyframes hide-smooth {
        to{
            opacity: 0;
        }
        from{
            opacity: 1;
        }
    }

</style>