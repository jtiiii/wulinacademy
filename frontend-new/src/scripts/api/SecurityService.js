// import Api from './Api';
import Api from './FetchApi';
// import Store from '../store';

const SecurityService = {
    __login__: {
        username: ''
    },
    login( username, password){
        // let _this = this;
        return Api.FormPost('/login',{username: username,password:password}).then( () => {
            // Store.state.isLogin = true;
        });
    },
    logout(){
        let _this = this;
        return Api.Post('/logout').then( () => {
                _this.__login__.username = '';
                // Store.state.isLogin = false;
        });
    },
    // isLogin: {
    //     value: false
    // }
};
export default SecurityService;