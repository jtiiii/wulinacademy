function FData( value , setter, getter){
    this.value = value;
    this.setter = setter;
    this.getter = getter;
}
function addSetterResolve(key,resolve){
    let fdata = Data[key];
    let oldSetter = fdata.setter;
    fdata.setter = value => {
        oldSetter(value);
        if(typeof resolve === 'function'){resolve(value);}
    };
    Object.defineProperty(DATA,key,{
        set: fdata.setter
    });
}
const Data ={
    status: new FData('manage'),
    isManage: new FData(false,undefined,()=>{
        return DATA.status === StaticData.status.Manage;
    })
};
const StaticData = {
    status: {
        Manage: 'manage',
        Show: 'show'
    }
};
const Methods ={
    openManage() {
        DATA.status = StaticData.status.Manage;
    },
    closeManage(){
        DATA.status = StaticData.status.Show;
    }
};


const DATA = {};
(()=>{
    for( let key in Data){
        let fdata = Data[key];
        let setter = fdata.setter ? fdata.setter : function(value){ Data[key].value = value; };
        let getter = fdata.getter ? fdata.getter : function(){ return Data[key].value; };
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

const Common = {
    data:DATA,
    methods:Methods,
    addDataResolve: addSetterResolve
};
export default Common;