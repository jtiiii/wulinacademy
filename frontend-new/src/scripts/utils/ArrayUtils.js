const ArrayUtils = {
    clean(arr){
        if(arr && arr instanceof Array){
            arr.splice(0);
        }
    },
    copy(source, target){
        ArrayUtils._requireArray(source, "source");
        ArrayUtils._requireArray(target, "target");
        ArrayUtils.clean(target);
        source.forEach( arr => target.push(arr));
    },
    isArray( arr ){
        return arr && arr instanceof Array;
    },
    notArray( arr ){
        return !ArrayUtils.isArray(arr);
    },
    _requireArray( arr, argName){
        argName = argName || "arr";
        if( ArrayUtils.notArray( arr )){
            throw new Error("The "+argName+" doesn't a array obj");
        }
    }
};
export default ArrayUtils;