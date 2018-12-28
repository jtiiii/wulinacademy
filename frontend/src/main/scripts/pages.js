import VueRouter from 'vue-router';
const Home = () => import('../pages/Home.vue');
const About = () => import('../pages/About.vue');
const PageError = () => import('../pages/Error.vue');
const News = () => import('../pages/News.vue');
const Channel = () => import('../pages/Channel.vue');
const Test = () => import('../pages/Test.vue');

const pages = [
    { id: 'news', path: '/news', component: News, i18key: 'index.nav.news', default: true},
    { id: 'about', path: '/about', component: About, i18key: 'index.nav.about'},
    { id: 'channel', path: '/channel', component: Channel, i18key: 'index.nav.channel'},
    { id: 'home', path: '/home', component: Home, i18key: 'index.nav.home' , hidden: true},
    { id: 'test' , path: '/test', component: Test, i18key: 'index.nav.test' , hidden: true},
    { id: 'apply', path: '/apply', component: PageError, i18key: 'index.nav.apply', hidden: true},
    { id: '' , path: '/*', component: PageError, i18key: 'index.nav.error', hidden: true}
];
const routes = [];
pages.forEach(value => {
    routes.push({ name: value.id, path:value.path, component: value.component });
});
export default new VueRouter({
    mode: 'hash',
    routes: routes
});
export {pages as Pages};
