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
                tab.text = this.$t(tab.i18key)
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
    mounted(){
        if(!this.$route || this.$route.path === '/'){
            this.go(this.defaultPage);
        }
    },
    created(){
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