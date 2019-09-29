class BarrierTask{
    constructor(){
        this.__tasks__ = [];
    }
    addTask( task ){
        this.__tasks__.push( task );
        return this;
    }
    run(){
        return new Promise( resolveAll => {
            let arr = [];
            this.__tasks__.forEach( task => {
                arr.push(undefined);
                //在new的时候,task就在执行了。
                let p =  new Promise( resolve => {
                    //将resolve 交给task，让task来自行判断什么时候触发下一步.then
                    task( resolve );
                });
                p.then( () => {
                    arr.shift();
                    if( arr.length === 0){
                        resolveAll();
                    }
                });
            });
        });
    }
}
export default BarrierTask;