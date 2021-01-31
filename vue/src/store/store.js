import Vue from 'vue';
import Vuex from 'vuex';
//import EB from 'vertx3-eventbus-client';
import state from "./state.js";
import getters from "./getters.js";
import mutations from "./mutations.js";
import actions from "./actions.js";

//import childA from "./modules/childA.js";
//import childB from "./modules/childB.js";
Vue.use(Vuex);
export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions/*,
  modules: {
    scoreBoard: childA,
    resultBoard: childB
  }*/
});