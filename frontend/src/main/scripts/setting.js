import Model from './components/BaseModel';
import {I18nLanguage, I18nLocale } from './i18n';
function SettingItem(id, i18Key, resolve,){
    Model.ListItem.apply(this,[id,I18nLocale.getMessage(i18Key),false]);
    this.resolve = resolve;
    this.i18key = i18Key;
}
SettingItem.prototype = new Model.ListItem();
SettingItem.prototype._refresh_ = function(){
    this.text = I18nLocale.getMessage( this.i18key );
};

function SettingGroupMenu(id, i18key, items){
    Model.GroupMenu.apply(this,[id,I18nLocale.getMessage(i18key),items]);
    this.i18key = i18key;
}
SettingGroupMenu.prototype = new Model.GroupMenu();
SettingGroupMenu.prototype._refresh_ = function(){
    this.name = I18nLocale.getMessage( this.i18key );
    this.items.forEach( item => item._refresh_());
};

const sets = [];
//初始化语言设置
(() => {
    let languagesItems = [];
    function changeLocale( item ){
        I18nLocale.code = item.id;
    }
    for(let code in I18nLanguage){
        languagesItems.push( new SettingItem(code,"i18n." + code, changeLocale ));
    }
    sets.push(new SettingGroupMenu('language','index.sets.language', languagesItems));
})();
I18nLocale.addResolve(()=>{
    sets.forEach( set => set._refresh_() );
});


export default sets;


