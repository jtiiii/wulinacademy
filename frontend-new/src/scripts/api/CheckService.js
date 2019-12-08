import ServiceApi from './ServiceApi';

const CheckService = {
    __api__: new ServiceApi("检查服务模块"),
    context:'/check',
    available(){
        return this.__api__.Get(this.context+ '/available',{});
    }
};

export default CheckService;