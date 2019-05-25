import VueRouter from 'vue-router';

const Home = resolve => require(['../pages/Home.vue'], resolve);
const About = resolve => require(['../pages/About.vue'], resolve);
const PageError = resolve => require(['../pages/Error.vue'], resolve);
const News = resolve => require(['../pages/News.vue'],resolve);
const Channel = resolve => require(['../pages/Channel.vue'],resolve);
const Test = resolve => require(['../pages/Test.vue'],resolve);
const Manager = resolve => require(['../pages/Manager.vue'],resolve);

const pages = [
    { name: 'home', path: '/home', component: Home, i18key: 'index.nav.home' , hidden: false, default: true},
    { name: 'news', path: '/news/:newsId', props: true, component: News, i18key: 'index.nav.news', defaultValue:{ newsId: "list"}, default: false},
    { name: 'about', path: '/about', component: About, i18key: 'index.nav.about'},
    { name: 'channel', path: '/channel', component: Channel, i18key: 'index.nav.channel'},
    { name: 'test' , path: '/test', component: Test, i18key: 'index.nav.test' , hidden: true},
    { name: 'apply', path: '/apply', component: PageError, i18key: 'index.nav.apply', hidden: true},
    { name: 'manager', path: '/manager', component: Manager, i18key: 'index.nav.manager', hidden: true},
    { name: '' , path: '/*', component: PageError, i18key: 'index.nav.error', hidden: true}
];
const routes = [];
pages.forEach(value => {
    routes.push({ name: value.name, path:value.path, props: value.props? value.props: false, component: value.component });
});
export default new VueRouter({
    mode: 'hash',
    routes: routes
});
export {pages as Pages};
