import Vue from 'vue';
import App from './App.vue';
// import router from './pages/pages';
import i18n from './scripts/i18n/i18n';
import store from './scripts/Store';

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  store,
  i18n,
  // router,
}).$mount('#app');
