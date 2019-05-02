import axios from 'axios';
import Common from '../Common';
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

const Handler = {
    SUCCESS( response ) {
        console.log('success', response);
        return response.data;
    },
    ERROR( error ){
        error = error.response.data || error;
        console.error(error);
        return error;
    }
};


function createClient(devMode){
    let baseUrl = devMode? Path.LOCAL: Path.PRODUCT;
    return axios.create({
        baseURL: baseUrl,
        crossDomain: true,
        withCredentials: true,
        headers:{'Content-Type': 'application/json;charset=utf-8'}
    });
}

const Api = {
    _axios_: createClient(true),
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
        return this._fetch_(url,{
            method: Method.POST,
            params: param,
            headers:{
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
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
        return this._axios_(url,option).then( Handler.SUCCESS ).catch( Handler.ERROR );
    }
};

export default Api;
export { Method };