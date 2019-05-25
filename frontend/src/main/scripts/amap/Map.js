import LoadJS from '../Loadjs';

const URL = 'https://webapi.amap.com/maps?v=1.4.10&key=1accfe63ebc155603fd447be7681e05d&callback=mapInit';
const UI_URL = 'https://webapi.amap.com/ui/1.0/main.js?v=1.0.11&callback=mapUIInit';

function Map(domId){
    this.map = new Map._amap.Map(domId);
    // 创建一个 Marker 实例：
    this.markers = {};
}
Map.prototype = {
    constructor : Map,
    marker: function({longitude, latitude, title}){
        this.markers[title] = new Map._amap.Marker({
            position: new Map._amap.LngLat(longitude, latitude),
            title: title,
        });
        this.map.add(this.markers[title]);
    },
    // addZoomControl: function(){
    //     this.map
    //     //添加一个缩放控件
    //     .addControl(new BasicControl.Zoom({
    //         position: 'lt'
    //     }));
    // }
};
Map.loadUIScript = function( ){
    return LoadJS.load(UI_URL);
};
//120.161118,30.246205
Map.init = function( resolve ){
    Map._amap = AMap;
    resolve(AMap);
};
Map.uiInit = function( resolve){
    AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {
        map.map.addControl(new BasicControl.Zoom({
            position: 'lt'
        }));
    });
    resolve( AMapUI );
};
Map.loadScript = function(){
    return new Promise( resolve => {
        window.mapInit = Map.init.bind(Map,resolve);
        let jsapi = document.createElement('script');
        jsapi.charset = 'utf-8';
        jsapi.src = URL;
        document.head.appendChild(jsapi);
    });
};
Map._amap = null;
Map.AMap = function(){
    return new Promise( resolve => {
        if( Map._amap ){
            resolve(Map._amap);
        }else{
            Map.loadScript().then( amap => {
                resolve(amap);
            });
        }
    });

};


function createPoint(){
    var marker = new AMap.Marker({
        position: new AMap.LngLat(116.39, 39.9),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        title: '武林书画院'
    });

    // 将创建的点标记添加到已有的地图实例：
    window.map.add(marker);
}

export default Map;