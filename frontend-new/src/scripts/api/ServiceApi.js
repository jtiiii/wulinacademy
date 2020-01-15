import Api from './FetchApi';
import ServerConfig ,{env} from "../config/ServerConfig";
import FUtils from 'fo-utils';
const StringUtils = FUtils.StringUtils;
const authToken = ServerConfig.localStorage.authToken;
const AUTH_TOKEN_HEADER_NAME = "auth-token";
class ServiceApi{
    constructor(name) {
        this.name = name;
        this.host = env.host;
        this.port = env.port;
        this.api = new Api(this.host,this.port);
    }
    setAuthToken( value ){
        this.token = value;
    }
    Get( url, {query, urlData}){
        let param = {
            query,
            urlData
        };
        return this.__fetchResult(this.api.Get(url,param), 'GET',url, param);
    }
    Post( url, {query, urlData,body}){
        let param = {
            query,
            urlData,
            body,
            headers:this.getAuthTokenHeader()
        };
        return this.__fetchResult(this.api.Post(url,param),'POST',url, param);
    }
    Put(url, {query, urlData,body}){
        let param = {
            query,
            urlData,
            body,
            headers:this.getAuthTokenHeader()
        };
        return this.__fetchResult(this.api.Put(url, param),'PUT',url, param);
    }
    Delete(url, {query, urlData}){
        let param = {
            query,
            urlData,
            headers:this.getAuthTokenHeader()
        };
        return this.__fetchResult(this.api.Delete(url,param),'DELETE',url, param);
    }
    __fetchResult(api, method, url, param){
        return api.then(response => this.__processResponses(response,method, url,param) ).catch(ServiceApi._errorHandler);
    }
    __processResponses(response,method,url,{ query, urlData, body , header}){
        return ServiceApi._responseHandler(response, {host: this.host, port: this.port, name: this.name, url, query, header, body, urlData, method });
    }
    getAuthTokenHeader(){
        let headers = {};
        headers[AUTH_TOKEN_HEADER_NAME] = this.token? this.token: authToken.get();
        return headers;
    }
    static _dataHandler( response ){
        if(response.headers.has('Content-Type')) {
            let contentType = response.headers.get('Content-Type');
            if (contentType.indexOf('application/json') !== -1) {
                return response.json();
            }
        }
        return response.text();
    }
    static _responseHandler(response,  {name, host, port, url, query, header, body, urlData, method}){
        return ServiceApi._dataHandler(response).then( resBody => {
            if(env.debug){
                ServiceApi._debugConsole(name, host, port, url,{query, header, body, urlData, method,resBody:resBody,resText: response.statusText, resStatus: response.status });
            }
            return resBody
        });
    }
    static _errorHandler(error){
        console.log("err:", error);
    }
    static _debugConsole(name,host, port, url,{ query, header, body, urlData, method, resBody, resStatus, resText}){
        method = StringUtils.fixLength(method,6, " ",true);
        let str = `%c Service Api Log %c  ${name} \n${method} - ${host}:${port}${url}\nrequest: %O \nresponse: %O` ;
        let css = 'background:#35495e ; padding: 1px; border-radius: 3px;  color: #fff';
        console.log(str,css,"",
            {
                urlData: urlData,
                query: query,
                header: header,
                body: body
            },
            {
                body: resBody,
                status: resStatus,
                statusText: resText,
            }
        );
    }
}

export default ServiceApi;