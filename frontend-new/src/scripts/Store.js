const store ={
    state:{
        isLogin: false,
        locale: 'en-US',
        page:'/home',
    },
    mutations:{
        login: state =>{
            state.isLogin = true;
        },
        logout: state => {
            state.isLogin = false;
        }
    }
};

export default store;