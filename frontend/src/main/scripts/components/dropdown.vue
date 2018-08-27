<template>
    <div class="dropDown" v-outsideclick="hideMenu">
        <button @click="showMenu()">
            {{ buttonText }} <slot></slot>
        </button>
        <div class="dropMenu" :class="direction" v-show="show">
            <list v-for="group in groups" :key="group.name" :items="group.items" :title="group.name" :is-button-list="true" :click="itemClick"></list>
        </div>
    </div>
</template>
<script type="text/javascript">
    import List from './list.vue';
    let groupValidator = function(group){
        return group.items instanceof Array;
    };
    const outsideclick = {
        bind: function(el, banding){
            let documentHandle = function(e){
                if(el.contains(e.target)){
                    return false;
                }
                if(banding.expression) {
                    banding.value(e);
                }
            };
            el._outsideclick_ = documentHandle;
            document.addEventListener("click", documentHandle);
        },
        unbind: function (el) {
            document.removeEventListener("click", el._outsideclick_);
            delete el._outsideclick_;
        }
    };
    export default {
        name: 'dropDown',
        components: {
            "list": List
        },
        props: {
            groups: {
                type: Array,
                required: false,
                default: () => [],
                validator: value => {
                    return value.every(groupValidator);
                }
            },
            buttonText: {
                type: String,
                required: false,
                default: '',
            },
            itemClick: {
                type: Function,
                required: false,
                default: () => {}
            },
            menuDirect: {
                type: String,
                required: false,
                default: 'left',
                validator: function( value ){
                    return value === 'left' || value === 'right';
                }
            }
        },
        directives:{
            outsideclick: outsideclick
        },
        computed: {
            direction: function(){
                let result = {};
                result[this.menuDirect] = true;
                return result;
            }
        },
        data: () =>{
            return {
                show: false
            }
        },
        methods: {
            showMenu: function(){
                this.show = true;
            },
            hideMenu: function(){
                this.show = false;
            }
        }
    }
</script>
<style scoped>
    @import url("../../styles/button.css");
    .dropDown{
        position: relative;
        display: inline-block;
    }
    .dropMenu{
        display: inline-block;
        padding: 10px;
        border: 1px solid #aaa;
        border-radius: 5px;
        box-shadow: 3px 2px 4px 0 #aaa;
        background: #fff;
        position: absolute;
        top: 29px;
        z-index: 2;
    }
    .left{
        left: 0;
    }
    .right{
        right: 0;
    }
</style>