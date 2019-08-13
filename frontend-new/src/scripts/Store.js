import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
const store = new Vuex({
    state:{
        isLogin: false,
        locale: 'en-US'
    },
});

export default store;