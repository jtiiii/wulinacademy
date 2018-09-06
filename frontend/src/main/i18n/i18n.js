import Vue from 'vue';
import VueI18n from 'vue-i18n';
import zh_CN from './zh-CN';
import en_US from './en-US';

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
    "zh-TW": "繁体中文",
    "en-US": "English - US"
};
const codes = [];
for(let code in I18nLanguage){
    codes.push(code);
}
export { codes as I18nCodes, I18nLanguage };
export default I18n;