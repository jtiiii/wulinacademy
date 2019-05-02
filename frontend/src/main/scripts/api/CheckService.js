import Api from './Api';

const CheckService = {
    context:'/check',
    available(){
        return Api.Get(this.context+ '/available');
    }
};

export default CheckService;