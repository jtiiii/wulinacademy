import VueRouter from 'vue-router';
import Home from '../pages/Home.vue';
import About from '../pages/About.vue';
import PageError from '../pages/Error.vue';
import News from '../pages/News.vue';
import Channel from '../pages/Channel.vue';
export default new VueRouter({
    routes: [
        { name: 'error', path: '/error', component: PageError},
        { name: 'home', path: '/home', component: Home},
        { name: 'about', path: '/about', component: About },
        { name: 'news', path: '/news', component: News},
        { name: 'channel', path: '/channel', component: Channel},
        { name: 'apply', path: '/apply', component: PageError},
    ]
});