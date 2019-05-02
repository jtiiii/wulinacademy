import User from './User';
import Authority from  './Authority';

function LoginUser({username,token,authorities = {}}) {
    User.apply(this,[{username,token}]);
    this.authorites = authorities;
}
LoginUser.prototype = new User({});
LoginUser.prototype.constructor = LoginUser;

LoginUser.prototype.addAuthority = function({name,path}){
    if(this.authorites[name]){
        return;
    }
    this.authorites[name] = new Authority({name,path});
};

export default LoginUser;

