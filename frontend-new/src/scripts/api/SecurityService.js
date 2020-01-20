import ServiceApi from "./ServiceApi";
import ServerConfig from "../config/ServerConfig";
import FetchUtils from "../utils/FetchUtils";

const SecurityService = {
    __api__: new ServiceApi("登陆服务"),
    login( username, password){
        return this.__api__.Post('/login',{body:FetchUtils.toFormData({username: username,password:password})})
            .then( token => {
                ServerConfig.localStorage.authToken.set(token);
            });
    },
    logout() {
        return this.__api__.Post('/logout',{}).then( () => {
            ServerConfig.localStorage.authToken.remove();
        });
    },
    hasToken(){
        return Boolean(ServerConfig.localStorage.authToken.get());
    }
};
export default SecurityService;