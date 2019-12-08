const store ={
    state:{
        isLogin: false,
        locale: 'en-US',
        page:'/home',
        mode: process.env.NODE_ENV,
    },
    getters:{
        isDev( state ){
            return state.mode === 'development';
        }
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