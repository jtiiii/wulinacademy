<template>
    <div class="dropDown" v-outsideclick="outSideClick">
        <button class="btn" @click="showMenu()">
            {{ buttonText }} <slot></slot>
        </button>
        <div class="dropMenu" :class="direction" v-show="show">
            <list v-if="!isDoubleArray" ref="groups" :items="groups" :title="groups.name" :is-button-list="true" :click="itemClick"   ></list>
            <list v-else :ref="group.name" v-for="group in groups" :key="group.name" :items="group.items" :title="group.name" :is-button-list="true" :click="groupItemClick(group)"></list>
        </div>
    </div>
</template>
<script type="text/javascript">
    import List from './BaseList.vue';
    import Model from './BaseModel';
    import Utils from '../utils';

    let groupValidator = function(groups){
        return groups.every( group => group instanceof Model.ListItem )
            || groups.every( group => group instanceof Model.GroupMenu );
    };
    export default {
        name: 'BaseDropDown',
        components: {
            "list": List
        },
        props: {
            groups: {
                type: Array,
                required: false,
                default: () => [],
                validator: groupValidator
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
            },
        },
        directives:{
            outsideclick: Utils.outsideClick
        },
        computed: {
            direction: function(){
                let result = {};
                result[this.menuDirect] = true;
                return result;
            },
            isDoubleArray: function(){
                return this.groups.every( group => group instanceof Model.GroupMenu );
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
                console.info('hideMenu');
            },
            groupItemClick: function( group ){
                let ic = this.itemClick;
                return function(item){
                    return ic(item,group.id);
                }
            },
            outSideClick: function(){
                if(this.show){
                    this.hideMenu();
                }
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