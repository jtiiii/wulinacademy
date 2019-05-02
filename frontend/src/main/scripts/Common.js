import FData from './FData';

const data ={
    status: 'manager',
    isManage: false
};
const __data__ = {};
const DATA = {};
(()=>{
    for( let key in data){
        let fdata = new FData(data[key]);
        __data__[key] = fdata;
        let setter = fdata.setter ? fdata.setter : function(value){ data[key] = value; };
        let getter = fdata.getter ? fdata.getter : function(){ return data[key]; };
        fdata.setter = setter;
        fdata.getter = getter;
        Object.defineProperty(DATA,key,{
            set: setter,
            get: getter,
            enumerable: true,
            configurable: true
        });
    }
})();
const Methods ={
};
const Common = {
    data:DATA,
    __data__:__data__,
    methods:Methods,
    addDataResolve: function(key,resolve){
        let fdata = this.__data__[key];
        let oldSetter = fdata.setter;
        fdata.setter = value => {
            oldSetter(value);
            if(typeof resolve === 'function'){resolve(value);}
        };
        Object.defineProperty(DATA,key,{
            set: fdata.setter
        });
    },
};

export default Common;