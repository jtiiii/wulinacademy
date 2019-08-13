import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const Home = resolve => require(['./Home.vue'], resolve);
const About = resolve => require(['./About.vue'], resolve);
const PageError = resolve => require(['./Error.vue'], resolve);
const News = resolve => require(['./News.vue'],resolve);
const Channel = resolve => require(['./Channel.vue'],resolve);
const Test = resolve => require(['./Test.vue'],resolve);
const Manager = resolve => require(['./Manager.vue'],resolve);

const pages = [
    { name: 'home', path: '/home', component: Home, i18key: 'index.nav.home' , hidden: false, default: true},
    { name: 'news', path: '/news', props: true, component: News, i18key: 'index.nav.news'},
    { name: 'news-id', path: '/news/:newsId', props: true, component: News, i18key: 'index.nav.news', default: false,  hidden: true},
    { name: 'about', path: '/about', component: About, i18key: 'index.nav.about'},
    { name: 'channel', path: '/channel', component: Channel, i18key: 'index.nav.channel'},
    { name: 'test' , path: '/test', component: Test, i18key: 'index.nav.test' , hidden: true},
    { name: 'apply', path: '/apply', component: PageError, i18key: 'index.nav.apply', hidden: true},
    { name: 'manager', path: '/manager', component: Manager, i18key: 'index.nav.manager', hidden: true},
    { name: 'error' , path: '/*', component: PageError, i18key: 'index.nav.error', hidden: true}
];
const mapPage = {};
const routes = [];
pages.forEach(value => {
    mapPage[value.name] = value;
    routes.push({ name: value.name, path:value.path, props: value.props? value.props: false, component: value.component });
});
const router = new VueRouter({
    mode: 'hash',
    routes: routes
});
export default router;
export {pages as Pages, mapPage};
