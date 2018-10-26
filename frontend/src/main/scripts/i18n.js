import Vue from 'vue';
import VueI18n from 'vue-i18n';
import zh_CN from '../i18n/zh-CN';
import en_US from '../i18n/en-US';

Vue.use(VueI18n);

const I18n = new VueI18n({
    locale: 'zh-CN',
    messages : {
        'zh-CN': zh_CN,
        'en-US': en_US
    }
});

const I18nLanguage = {
    "zh-CN": "简体中文",
    // "zh-TW": "繁体中文",
    "en-US": "English - US"
};
const codes = [];
for(let code in I18nLanguage){
    codes.push(code);
}

const Locale ={};
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
Locale._resolves_ = [];
Locale.getMessage = function( i18nPath ){
    let message = I18n.getLocaleMessage(I18n.locale);
    i18nPath.split('.').forEach( key => {
        message = message[key]
    });
    return message;
};

export { codes as I18nCodes, I18nLanguage, Locale as I18nLocale };
export default I18n;