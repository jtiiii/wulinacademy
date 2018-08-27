<template>
    <dl>
        <dt v-if="title"><hr/><span>&nbsp;{{ title }}&nbsp;</span></dt>
        <dd v-for="item in items" :key="item.id" :dd-id="item.id" ><span v-if="!isButtonList">{{ item.text }}</span><button v-else @click="click(item)" >{{ item.text }}</button></dd>
    </dl>
</template>
<script type="text/javascript">
    let validateItem = function(item){
        if(!item){
            return false;
        }
        if(!item.id){
            return false;
        }
        let id = String(item.id);
        return !(id === 'null' || id === '');
    };
    export default {
        name: 'list',
        props: {
            items:{
                type: Array,
                required: true,
                validator: value => {
                    if(!value instanceof Array){
                        return false;
                    }
                    return value.every(validateItem);
                }
            },
            title:{
                type: String,
                required: false,
                default: undefined
            },
            isButtonList: {
                type: Boolean,
                required: false,
                default: false
            },
            click: {
                type: Function,
                required: false,
                default: ()=>{}
            }
        }
    }
</script>
<style scoped>
    @import url("../../styles/button.css");
    @import url("../../styles/dl-list.css");
    hr{
        top: 1px;
        position: absolute;
        width: 100%;
        z-index: -1;
    }
    dt{
        position: relative;
        text-align: center;
        color: #999;
    }
    dt > span{
        background: #fff;
    }
    button:hover{
        background: #eee;
    }
    button:active{
        background: #ddd;
    }
    button{
        white-space: nowrap;
        padding: 0 10px;
        width: 100%;
        outline: none;
        border: none;
        text-align: left;
        cursor: pointer;
    }
</style>
