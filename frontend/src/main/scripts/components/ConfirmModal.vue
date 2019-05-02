<template>
    <div>
        <v-base-modal :show="show" :width="width" :height="height">
            <div>
                <slot></slot>
            </div>
            <div class="btn-area">
                <button type="button" class="btn btn-ok" @click="confirm(true)"> OK </button>
                <button type="button" class="btn" @click="confirm(false)"> Cancel </button>
            </div>
        </v-base-modal>
    </div>
</template>
<script>
    import BaseModal from './BaseModal.vue';
    export default {
        components:{
            'v-base-modal': BaseModal
        },
        props:{
            show:{
                type: Boolean,
                required: false,
                default: true,
            },
            width: {
                type: Number,
                required: false
            },
            height: {
                type: Number,
                required: false
            }
        },
        methods:{
            confirm:function(flag){
                this.$emit('confirm',flag);
            }
        }

    };
</script>
<style scoped>
    @import url('../../styles/button.css');
    .btn-area{
        margin: 5px 0 0 0;
    }
</style>