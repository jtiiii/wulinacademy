const Home = resolve => require(['./Home.vue'], resolve);
const About = resolve => require(['./About.vue'], resolve);
// const PageError = resolve => require(['./Error.vue'], resolve);
const News = resolve => require(['./News.vue'],resolve);
const Channel = resolve => require(['./Channel.vue'],resolve);
// const Test = resolve => require(['./Test.vue'],resolve);
const Manager = resolve => require(['./Manager.vue'],resolve);

const pages = [
    { name: 'home', path: '/home', i18key: 'index.nav.home' , hidden: false, default: true,
        component: Home,
    },
    { name: 'news', path: '/news', props: true, i18key: 'index.nav.news',
        component: News,
    },
    { name: 'news-id', path: '/news/:newsId', props: true, i18key: 'index.nav.news', default: false,  hidden: true,
        component: News
    },
    { name: 'about', path: '/about', i18key: 'index.nav.about',
        component: About
    },
    { name: 'channel', path: '/channel', i18key: 'index.nav.channel',
        component: Channel
    },
    { name: 'test' , path: '/test', i18key: 'index.nav.test' , hidden: true,
        // component: Test,
    },
    { name: 'apply', path: '/apply', i18key: 'index.nav.apply', hidden: true,
        // component: PageError,
    },
    { name: 'manager', path: '/manager', i18key: 'index.nav.manager', hidden: true,
        component: Manager,
    },
    { name: 'error' , path: '/*', i18key: 'index.nav.error', hidden: true,
        // component: PageError,
    }
];
const mapPage = {};
const routes = [];
pages.forEach(value => {
    mapPage[value.name] = value;
    routes.push({ name: value.name, path:value.path, props: value.props? value.props: false, component: value.component });
});
// const router = new VueRouter({
//     mode: 'hash',
//     routes: routes
// });
export default routes;
export {pages as Pages, mapPage};
