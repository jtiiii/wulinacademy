import fetch from 'fetch';

const UAT = 'http://47.99.153.169:9000';
const DEV = 'http://localhost:9000';
const Api = {
    __context__: UAT,
    __token__ : 'stranger',
    FormPost(url,data){
        let param = new FormData();
        Object.keys(data).forEach( key => {
            param.append(key,data[key]);
        });
        return this._restFetch(url,{
            method: 'post',
            body: param,
        });
    },
    Post(url,data){
        return this._restFetch(url,{
            method: 'post',
            body: JSON.stringify(data)
        });
    },
    Put(url,data){
        return this._restFetch(url,{
            method: 'put',
            body: JSON.stringify(data)
        });
    },
    Delete( url){
        return this._restFetch(url,{
            method: 'delete'
        });
    },
    Get(url,data){
        let param = new URLSearchParams();
        if(data){
        Object.keys(data).forEach( key => {
            param.append(key,data[key]);
        });
        }
        return this._restFetch(url+'?'+param.toString(),{
            method: 'get'
        });
    },
    _restFetch(url,option){
        url = this.__context__ + url;
        this._addToken(option);
        this._preFetch(option);
        return fetch(url,option)
            .then( res => {
                this._checkStatus(res);
                this._resetToken(res);
                return this._dataHandler(res);
            });
    },
    _addToken: function( option ){
        if(!option.headers) {
            option.headers = {};
        }
        option.headers['auth-token'] = this.__token__;
    },
    _preFetch: function( option ){
        option.mode = 'cors';
        option.credentials = 'include';
        if(!option.headers) {
            option.headers = {};
        }
        if(!option.headers['Content-Type'] && !(option.body instanceof FormData) ){
            option.headers['Content-Type'] = 'application/json;charset=utf-8';
        }

    },
    _resetToken: function( response ){
        if(response.headers.has('auth-token')){
            this.__token__ = response.headers.get('auth-token');
        }
    },
    _checkStatus: function( response ){
        if(!response.ok){
            let err =  new Error(response.status);
            err.response = response;
            throw err;
        }
    },
    _dataHandler: function( response ){
        if(response.headers.has('Content-Type')) {
            let contentType = response.headers.get('Content-Type');
            if (contentType.indexOf('application/json') !== -1) {
                return response.json();
            }
        }
        return response.text();
    }
};

export default Api;