import router,{Pages} from '../../pages';
import FComponents from 'f-vue-components';
import menuIcon from '../../../icons/list-icon.png';

const Option = {
    components:{
        'navigator': FComponents.Layout.Navigator,
        'dropdown': FComponents.Dropdown
    },
    data(){
        return {
            menu:{
                icon: menuIcon,
                show: false,
                tabs: []
            },
            defaultTab: null
        };
    },
    computed:{
        tabs(){
            return this.menu.tabs.map( tab => {
                if(tab.default){
                    this.defaultPage = tab;
                }
                tab.text = this.$t(tab.i18key);
                return tab;
            });
        }
    },
    methods:{
        aClick( tab ){
            this.go(tab.get());
            this.menu.show = false;
        },
        go( page ){
            router.push({ name: page.name,params: page.defaultValue });
        }
    },
    created(){
        this.$router.afterEach( to => {
            if(to.path === '/'){
                this.go(this.defaultPage);
            }
        });
        this.menu.tabs = Pages.filter( page => {
            if(page.default){
                this.defaultPage = page;
            }
            page.text = this.$t(page.i18key);
            return !page.hidden;
        });
    }
};
export default Option;