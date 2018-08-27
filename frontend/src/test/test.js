import Vue from 'vue';
import Dropdown from '../main/scripts/components/dropdown.vue';
import List from '../main/scripts/components/list.vue';
import TopBar from '../main/scripts/components/topbar.vue';

import logo from '../main/images/logo.png';

window.dropdown = new Vue({
    el: '#dropdown',
    components:{
        "dropdown": Dropdown
    },
    data: {
        buttonText: '来啊，来点我啊！',
        groups:[
            {
                name: 'list-1',
                items: [{id:"12",text:"12"},{id:"14",text:"14"},{id:"15",text:"15"}]
            },
            {
                name: 'list-2',
                items: [
                    {id: 'test-1', text: '测试1'},
                    {id: 'test-2', text: '测试2sssssssssssssssssssssssssssssssssssdfdsafeagew'}
                ]
            }
        ]


    }
});

window.list = new Vue({
    el: '#list',
    components: {
        "list":List
    },
    data: {
        items: [{id:"12",text:"12"},{id:"14",text:"14"},{id:"15",text:"15"}],
        title: "test - list",
        isButtonList: true,
        click: function(item){
            console.log('click '+ item.id);
        }
    }
});

window.topBar = new Vue({
    el:'#topbar',
    components: {
        "topbar": TopBar,
    },
    data:{
        nav: [{items:[
                {id: "about", text: 'About'},
                {id: 'channel', text: 'Channel'}
            ]}],
        logoSrc: logo,
        sets: [{items:[
                {id: 'zh_CN', text: '中文简体'},
                {id: 'en_US', text: 'English'}
            ], name:'language'}],
        currentTitle: 'About'
    }
});


