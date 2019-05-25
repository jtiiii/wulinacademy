import Map from "./amap/Map";

const LoadJS = {
    load(url){
        return new Promise(resolve => {
            let jsapi = document.createElement('script');
            jsapi.characterSet = 'utf-8';
            jsapi.charset = 'utf-8';
            jsapi.src = url;
            // window.mapUIInit = Map.uiInit.bind( Map, resolve );
            document.head.appendChild(jsapi);
            jsapi.onload = resolve.bind(jsapi);
        });

    }
};
export default LoadJS;