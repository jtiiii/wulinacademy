import VueRouter from 'vue-router';
import Home from '../pages/Home.vue';
import About from '../pages/About.vue';
import PageError from '../pages/Error.vue';
import News from '../pages/News.vue';

export default new VueRouter({
    routes: [
        { path: '/error', component: PageError},
        { path: '/home', component: Home},
        { path: '/about', component: About },
        { path: '/news', component: News}
    ]
});