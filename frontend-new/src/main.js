import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router';
import VueI18n from 'vue-i18n';

import App from './App.vue';

import routes from './pages/pages';
import i18nOption from './scripts/i18n/i18n';
import option from './scripts/Store';
import ServerConfig from "./scripts/config/ServerConfig";
Vue.use(Vuex);
Vue.use(VueI18n);
Vue.use(VueRouter);

Vue.config.productionTip = false;

const store = new Vuex.Store(option);
const router = new VueRouter({
  mode: 'hash',
  routes: routes
});
//添加导航首位，自动重定向到/home
router.beforeEach((to, from, next) => {
  if(to.path === '/'){
    next('/home');
    return;
  }
  next();
});
const i18n = new VueI18n(i18nOption);
new Vue({
  render: h => h(App),
  router,
  store,
  i18n,
}).$mount('#app');
let author = ServerConfig.author.asciiArt +
    "\n author :    Funeral Objects" +
    "\n email  :    " + ServerConfig.author.email +
    "\n github :    " + ServerConfig.author.github +
    "\n gitee  :    " + ServerConfig.author.gitee;
console.log("%c"+author, "color:#99ccff;");