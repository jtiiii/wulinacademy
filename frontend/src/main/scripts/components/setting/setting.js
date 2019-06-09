import FComponents from 'f-vue-components';
import setIcon from '../../../icons/setting-icon.png';
import {I18nLanguage,I18nLocale} from "../../i18n";

function initLanguageGroup(){
    function switchLanguage( tab ){
        I18nLocale.code = tab.code;
    }
    let languageTabs = Object.keys(I18nLanguage).map( key => {
        return { text: I18nLanguage[key], code: key, click: switchLanguage };
    });
    return { i18Key: 'index.sets.language', tabs: languageTabs };
}

const Option = {
    components:{
        'dropdown': FComponents.Dropdown,
        'navigator': FComponents.Layout.Navigator,
    },
    data(){
        return {
            dropdown:{
                icon: setIcon
            },
            menu:{
                groups: [],
                show: false,
            }
        };
    },
    methods:{
        click(tab){
            if(typeof tab.get().click === 'function'){
                tab.get().click( tab.get() );
            }
            this.menu.show = false;
        }
    },
    created(){

        this.menu.groups.push( initLanguageGroup() );
    }
};
export default Option;