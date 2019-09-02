import Vue from '$vue';
import Vuex from 'vuex';

Vue.use(Vuex);
const store = new Vuex.Store({
    state:{
        isLogin: false,
        showLoginBox: false,
        showLoginBoxForbid: false,
    },
    mutations:{
        setLogin: (state, login)=> {
            state.islogin = login;
        },
        setShowLoginBox: (state, showLoginBox) => {
            if(state.showLoginBoxForbid){
                state.showLoginBoxForbid = false;
                return;
            }
            state.showLoginBox = showLoginBox;
        },
        setShowLoginBoxForbid: (state, forbid) => {
            state.showLoginBoxForbid = forbid;
        }
    }
});

export default store;
