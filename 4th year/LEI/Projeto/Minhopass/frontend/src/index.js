import Vue from 'vue';
import * as VeeValidate from 'vee-validate';
import vuetify from './plugins/vuetify' // path to vuetify export

import { store } from './_store';
import { router } from './_helpers';
import App from './app/App';


import "@babel/polyfill";


Vue.use(VeeValidate);

new Vue({
    el: '#app',
    router,
    store,
    vuetify,
    render: h => h(App)
});