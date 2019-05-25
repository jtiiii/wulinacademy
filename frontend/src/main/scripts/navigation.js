// 导航栏
import {Pages} from './pages';
import Model from './components/BaseModel';
import { I18nLocale } from './i18n';

/**
 * 继承Model.ItemList
 * @param name 名称
 * @param path 路由地址
 * @param i18key 国际化Key
 * @constructor
 */
function NavItem({name,path,i18key,defaultValue}){
    Model.ListItem.apply(this,[name,I18nLocale.getMessage(i18key),false]);
    this.i18key = i18key;
    this.path = path;
    this.name = name;
    this.defaultValue = defaultValue;
}
NavItem.prototype = new Model.ListItem();
NavItem.prototype._refresh_= function(){
    this.text = I18nLocale.getMessage(this.i18key);
};

const Nav = {
    default: null,
    items: [],
    map: {}
};
Pages.forEach( page => {
    let item = new NavItem(page);
    if(!page.hidden){
        Nav.items.push(item);
    }
    Nav.map[item.path] = item;
    if(page.default){
        Nav.default = item;
    }
});

//刷新
function navRefresh(){
    Nav.items.forEach( item => {
        item._refresh_();
    });
}
I18nLocale.addResolve( navRefresh );

export default Nav;