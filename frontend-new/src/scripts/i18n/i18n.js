// import Vue from 'vue';
// import VueI18n from 'vue-i18n';
import zh_CN from '../i18n/zh-CN';
import en_US from '../i18n/en-US';
import zh_TW from '../i18n/zh-TW';

// Vue.use(VueI18n);
const Language = {
    'zh-TW': {message: zh_TW, text: "正體中文"},
    'zh-CN': {message: zh_CN, text: "简体中文"},
    'en-US': {message: en_US, text: "English"}
};
let messages = {};
const I18nLanguage = {};
for(let code in Language){
    messages[code] = Language[code].message;
    I18nLanguage[code] = Language[code].text;
    //在每个语言文件里面添加一份 i18n 翻译
    messages[code].i18n = I18nLanguage;
}
const I18n = {
    locale: 'en-US',
    messages: messages,
};

const Locale ={};
Locale._resolves_ = [];
Object.defineProperty(Locale,'code',{
    get: function(){
        return I18n.locale;
    },
    set: function( value ){
        let old = this.code;
        I18n.locale = value;
        this._resolves_.forEach(resolve => resolve( old, value ));
    }
});
Locale.addResolve = function ( resolve ){
    this._resolves_.push(resolve);
};
Locale.getMessage = function( i18nPath ){
    let message = I18n.getLocaleMessage(I18n.locale);
    i18nPath.split('.').forEach( key => {
        message = message[key]
    });
    return message;
};

export { I18nLanguage, Locale as I18nLocale };
export default I18n;