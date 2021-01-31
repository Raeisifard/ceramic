import Vue from 'vue'
//import Vue from 'vue/dist/vue.js'
import App from './App.vue'
import router from './router'
import store from './store/store'
import axios from 'axios'

Vue.prototype.$http = axios;
/*import Vuetify from 'vuetify'*/
//import 'vuetify/dist/vuetify.min.css'
/*Vue.use(Vuetify);*/
import 'vx-easyui/dist/themes/default/easyui.css';
import 'vx-easyui/dist/themes/icon.css';
import 'vx-easyui/dist/themes/vue.css';
import EasyUI from 'vx-easyui';
import { EventBus } from './event-bus.js';
//import switchButtons from './components/tools/mxSwitch/switchButtons.vue';

Vue.use(EasyUI);//, switchButtons);
//Vue.use(router);
Vue.config.productionTip = false;
//Add unimported components to ignore list to prevent warnings.
Vue.config.ignoredElements = ['field','block','category','xml','mutation','value','sep'];
Vue.prototype.$log = console.log.bind(console);

const vue = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
//vue.$store.commit("SET_VERSION", "1.0.0");
//Global preparation ...
/*Defining parseFunction method on String objects*/
if (typeof String.prototype.parseFunction != 'function') {
  String.prototype.parseFunction = function() {
    let funcReg = /function *\(([^()]*)\)[ \n\t]*{(.*)}/gmi;
    let match = funcReg.exec(this.replace(/\n/g, ' '));

    if (match) {
      return new Function(match[ 1 ].split(','), match[ 2 ]);
    }

    return null;
  };
}


