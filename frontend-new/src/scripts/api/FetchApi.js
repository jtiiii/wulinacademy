import fetch from 'node-fetch';

class Api{
    constructor(host, port) {
        this.host = host;
        this.port = port;
    }
    Post(url,{urlData,body, query,headers}){
        return this._baseFetch(url,"post",{urlData: urlData, body: body, query: query, headers: headers});
    }
    Put(url,{urlData,body, query, headers}){
        return this._baseFetch(url,"put",{urlData: urlData, body: body, query: query, headers: headers});
    }
    Delete( url, {query, urlData, headers}){
        return this._baseFetch(url,"delete",{urlData: urlData, query: query, headers: headers});
    }
    Get(url,{query,urlData, headers, cors = true}){
        return this._baseFetch(url,"get",{urlData: urlData, query: query, headers: headers, cors});
    }
    _baseFetch(url, method, {urlData, query, body, headers, cors = true} ){
        url = Api._processUrl(this.host,this.port,url);
        if(urlData){
            url = Api._processUrlTemplate(url,urlData);
        }
        if(query){
            url += "?"+Api._processQuery(query);
        }
        let option = {
            method: method,
            headers: headers,
        };
        if(body){
            option.body = Api._processBody(body);
        }
        if(cors){
            option.mode = 'cors';
        }
        return Api._restFetch(url,option);
    }
    static _restFetch(url,option){
        Api._preFetch(option);
        return fetch(url,option)
            .then( res => {
                Api._checkStatus(res);
                return res;
            });
    }
    static _processUrl(host, port, url){
        url = url ? url.startsWith("/")? url: "/" + url : "";
        return port? host + ":" + port + url : host + url;
    }
    static _preFetch( option ){
        option.credentials = 'include';
        if(!option.headers || !Object.keys(option.headers).length) {
            option.headers = {};
        }
        if(!option.headers['Content-Type'] && !(option.body instanceof FormData) ){
            option.headers['Content-Type'] = 'application/json;charset=utf-8';
        }
    }
    static _checkStatus( response ){
        if(!response.ok){
            let err =  new Error(response.status);
            err.response = response;
            throw err;
        }
    }
    static _processUrlTemplate(url, urlParam){
        let re = Api._getUrlRegExp();
        let matchResult = url.match(re);
        if(matchResult){
            matchResult.forEach( p => {
                let key = p.substring(1,p.length - 1);
                while ( url.indexOf( p ) !== -1){
                    url= url.replace(p,urlParam[key]);
                }
            });
        }
        return url;
    }
    static _processBody(body){
        if(!body){
            return "";
        }
        if(body instanceof FormData){
            return body;
        }
        if(typeof body === 'string'){
            return body;
        }
        return JSON.stringify(body);

    }
    static _processQuery(query){
        if(!query){
            return "";
        }
        if(query instanceof URLSearchParams){
            return query.toString();
        }
        let params = new URLSearchParams();
        Object.keys(query).forEach( key => {
            params.append(key,query[key]);
        });
        return params.toString();
    }
    static _getUrlRegExp(){
        return new RegExp("{.*?}", "g");
    }
}

export default Api;