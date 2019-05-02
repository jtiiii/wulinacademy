import Api from './Api';
const SecurityService = {
    __login__: {
        username: '',
        token: false
    },
    login( username, password){
        this.isLogin.value = true;
        // if(username && password){
        //     Api.FormPost('/login',{username: username,password:password});
        // }else{
        //     console.error("error!");
        // }
        return new Promise( resolve => resolve(true));
        // return this.isLogin.value;
    },
    isLogin: {
        value: false
    }
};
export default SecurityService;