// import Api from './Api';
import Api from './FetchApi';
const SecurityService = {
    __login__: {
        username: ''
    },
    login( username, password){
        let _this = this;
        return Api.FormPost('/login',{username: username,password:password}).then( () => {
            _this.isLogin.value = true;
        });
    },
    logout(){
        let _this = this;
        return Api.Post('/logout').then( () => {
                _this.__login__.username = '';
                _this.isLogin.value = false;
        });
    },
    isLogin: {
        value: false
    }
};
export default SecurityService;