const FetchUtils = {
    toFormData(data){
        let result = new FormData();
        Object.keys(data).forEach( key => {
            result.append(key, data[key]);
        });
        return result;
    }
};

export default FetchUtils;