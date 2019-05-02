const StringUtils = {
    __getRegExp__: () => new RegExp("{[0-9]+}","g"),
    format( str , args){
        let temp = null;
        let result = str;
        let regExp = this.__getRegExp__();
        do{
            temp = regExp.exec(str);
            if(temp == null){
                break;
            }
            temp = temp[0];
            let position = Number(temp.substr(1,temp.length - 2));
            result = result.replace(temp,args[position])
        }while ( true ) ;
        return result;
    },
    fixLength( str, length ,join ) {
        if (!join) {
            join = '';
        }
        return (Array(length).join(join) + str).slice(-length);
    },
    isString( obj ){
        return typeof obj === 'string' && obj.constructor === String;
    }
};

export default StringUtils;