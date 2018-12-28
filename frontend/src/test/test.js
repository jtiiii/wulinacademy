import Vue from 'vue';
import BaseModel from '../main/scripts/components/BaseModal.vue';

Vue.component('v-modal', BaseModel);

window.app = new Vue({
    el: '#app'
});