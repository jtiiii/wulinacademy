import StringUtils from './StringUtils';

const DateUtils = {

    getNowFullDateString(){
        return this.getFullDateString(new Date());
    },
    getNowDateTimeString( separator ){
        return this.getDateTimeString(new Date(),separator);
    },
    getFullDateString( date ){
        return StringUtils.format("{0}-{1}-{2}",
            [date.getFullYear(),StringUtils.fixLength((date.getMonth() + 1),2,"0"),date.getDate()]);
    },
    getDateTimeString( date, separator ){
        if(!separator){
            separator = 'T';
        }
        return StringUtils.format("{0}-{1}-{2}"+separator+"{3}:{4}:{5}",
            [
                date.getFullYear(),
                StringUtils.fixLength((date.getMonth() + 1),2,"0"),
                StringUtils.fixLength(date.getDate(),2,"0"),
                StringUtils.fixLength(date.getHours(),2,"0"),
                StringUtils.fixLength(date.getMinutes(),2,"0"),
                StringUtils.fixLength(date.getSeconds(),2,"0")
            ]);
    }

};

export default DateUtils;