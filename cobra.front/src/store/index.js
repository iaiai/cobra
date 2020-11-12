import Vue from 'vue'
import Vuex from 'vuex'
import msg from './module/msg'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {},
    mutations: {},
    actions: {},
    modules: {
        msg,
    },
})