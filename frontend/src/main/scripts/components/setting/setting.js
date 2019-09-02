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
            },
            refreshTabs: [
                {
                    i18Key: 'manager.managerMode',
                    tabs:[{text: this.$t('login.login'), i18key: 'login.login', code: 'manager-login', click: this.loginShow.bind(this) }]
                }
            ],
        };
    },
    watch:{
        locale( l ){
            this.refreshTabs.forEach( tabs => {
                tabs.tabs.forEach( tab => {
                    tab.text = this.$t(tab.i18key);
                });
            });
        }
    },
    methods:{
        click(tab){
            if(typeof tab.get().click === 'function'){
                tab.get().click( tab.get() );
            }
            this.menu.show = false;
        },
        loginShow(){
            this.$store.commit('setShowLoginBox', true);
            this.$store.commit('setShowLoginBoxForbid', true);
        }
    },
    computed:{

        locale(){
            return this.$i18n.locale;
        }
    },
    created(){
        this.menu.groups.push( initLanguageGroup() );
        this.refreshTabs.forEach( tab => {
            this.menu.groups.push( tab  );
        });

    }
};
export default Option;