import Api from './FetchApi';

const SecurityService = {
    login( username, password){
        return Api.FormPost('/login',{username: username,password:password});
    },
    logout() {
        return Api.Post('/logout');
    }
};
export default SecurityService;