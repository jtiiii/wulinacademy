import axios from 'axios';

const Scheme = {
    HTTP: 'http',
    HTTPS: 'https'
};

const Path = {
    LOCAL: Scheme.HTTP + '://localhost:9000',
    PRODUCT: Scheme.HTTPS + '://'
};


const Method ={
    GET: 'GET',
    POST: 'POST',
    PUT: 'PUT',
    DELETE: 'DELETE'
};
const AuthToken = {
    header: 'auth-token',
    stranger: 'stranger'
};

const Handler = {
    SUCCESS( response ) {
        console.log('success', response);
        let result = ResponseModel.of(response);
        console.log('result',result);
        return result;
    },
    ERROR( error ){
        error = error.response.data || error;
        console.error(error);
        return error;
    }
};

function ResponseModel({data, status}){
    this.data = data;
    this.status = status;
}
ResponseModel.prototype.isOK = function(){
    return (this.status - 200) < 100;
};
ResponseModel.of = function(obj){
    return obj instanceof ResponseModel? obj: new ResponseModel(obj);
};


function createClient(devMode,authToken){
    let baseUrl = devMode? Path.LOCAL: Path.PRODUCT;
    let headers = {'Content-Type': 'application/json;charset=utf-8'};
    headers[AuthToken.header] = authToken;
    return axios.create({
        baseURL: baseUrl,
        crossDomain: true,
        withCredentials: true,
        headers: headers
    });
}

const Api = {
    _axios_: createClient(true,AuthToken.stranger),
    _authToken_: AuthToken.stranger,
    Get(url, param){
        return this._get(url, param);
    },
    Post(url, param){
        return this._post(url, param);
    },
    FormPost(url,param){
        return this._form_post(url,param);
    },
    Put(url, param){
        return this._put(url, param);
    },
    Delete(url,param){
        return this._delete(url,param);
    },
    _get(url, param){
        return this._fetch_(url,{
            method: Method.GET,
            params: param
        });
    },
    _form_post(url,param){
        let data = new FormData();
        Object.keys( param ).forEach( key => {
            data.append(key,param[key]);
        });

        return this._fetch_(url,{
            method: Method.POST,
            params: param,
            headers:{
                'Content-Type': 'multipart/form-data'
            }
        });
    },
    _post(url, param){
        return this._fetch_(url,{
            method: Method.POST,
            data: JSON.stringify(param)
        });
    },
    _put(url, param){
        return this._fetch_(url,{
            method: Method.PUT,
            data: JSON.stringify(param)
        });
    },
    _delete(url,param){
        return this._fetch_(url,{
            method: Method.DELETE,
            params: param
        });
    },
    _fetch_(url,option){
        let _api = this;
        option.url = url;
        option.headers['Content-Type'] = 'multipart/form-data';
        return this._axios_(option).then( response => {
            if(response.headers[AuthToken.header]){
                _api.authToken = response.headers[AuthToken.header];
            }
            return response;
        }).then( Handler.SUCCESS ).catch( Handler.ERROR );
    }
};

Object.defineProperty(Api,"authToken",{
    set: function( token ){
        if( token === this._authToken_){
            return;
        }
        this._authToken_ = token;
        this._axios_ = createClient(true,token);
    },
    get: function(){
        return this._authToken_;
    }
});

export default Api;
export { Method };