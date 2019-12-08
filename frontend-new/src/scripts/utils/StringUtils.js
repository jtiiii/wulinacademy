const StringUtils = {
    __getRegExp__: () => new RegExp("{[0-9]+}","g"),
    format( str , args){
        let temp = null;
        let result = str;
        let regExp = this.__getRegExp__();
        do{
            temp = regExp.exec(str);
            if(temp != null){
                let tempStr= temp[0];
                let position = Number(tempStr.substr(1,tempStr.length - 2));
                result = result.replace(temp,args[position])
            }
        }while ( temp != null ) ;
        return result;
    },
    fixLength( str, length ,join, right ) {
        if (!join) {
            join = '';
        }
        if(right){
            return (str + Array(length).join(join)).substring(0,length);
        }
        return (Array(length).join(join) + str).slice(-length);
    },
    isString( obj ){
        return typeof obj === 'string' && obj.constructor === String;
    }
};

export default StringUtils;