import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
     examFinish:false //用来表示考试倒计时是否为0
  },
  mutations: {
    finish (state){
      state.examFinish = true;
      console.log(state.examFinish);
    }
  },
  actions: {}
});
