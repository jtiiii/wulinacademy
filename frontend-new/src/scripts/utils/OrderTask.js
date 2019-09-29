class OrderTask{
    constructor(){
        this.__p = new Promise( resolve => resolve('start'));
        this.__task__ = [];
        this.__start__ = false;
    }
    addTask( task ){
        this.__task__.push( task );
        if(this.__start__){
            this.run();
        }
        return this;
    }
    start(){
        this.run();
        this.__start__ = true;
    }
    hasNext(){
        return this.__task__.length > 0;
    }
    run(){
        let task = this.__task__.shift();
        if(task){
            this.__p.then( () => new Promise( resolve => { resolve( task() )})).then( () => {
                if(this.hasNext()){this.run();}
            });
        }
    }
}

export default OrderTask;