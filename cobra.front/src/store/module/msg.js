//添加异常
export const MESSAGE_CLEAN = 'MESSAGE_CLEAN'    //清空
export const MESSAGE_ADD = 'MESSAGE_ADD'    //新消息
export const MESSAGE_ADDS = 'MESSAGE_ADDS'    //新消息
export const MESSAGE_DEL = 'MESSAGE_DEL'    //删除消息
export const MESSAGE_READ = 'MESSAGE_READ'    //设置已读

export default {
    state: {
        messages: [],
    },
    mutations: {    //同步执行
        [MESSAGE_READ](state,id) {
            //设置为此id为已读取，从列表中删除
            if(state.messages.length>0){
                for(var i=0;i<state.messages.length;i++){
                    if(state.messages[i].id==id) {
                        state.messages.splice(i,1)
                        return
                    }
                }
            }
        },
        [MESSAGE_CLEAN](state) {
            state.messages = []
        },
        [MESSAGE_ADD](state, messages) {
            state.messages.push(messages)
            // state.messages = state.messages.concat(messages)
        },
        [MESSAGE_ADDS](state, messages) {
            state.messages = state.messages.concat(messages)
        },
        [MESSAGE_DEL](state, id) {
            for (var i = 0; i < state.messages.length; i++) {
                if (id == state.messages[i].id) {
                    state.messages.splice(i, 1);
                }
            }
        },
    },
    actions: {  //异步执行
        [MESSAGE_READ]({commit},id) {
            return new Promise((resolve, reject) => {
                commit(MESSAGE_READ,id)
                resolve()
            })
        },
        [MESSAGE_CLEAN]({commit}) {
            return new Promise((resolve, reject) => {
                commit(MESSAGE_CLEAN)
                resolve()
            })
        },
        [MESSAGE_ADD]({commit}, messages) {
            return new Promise((resolve, reject) => {
                commit(MESSAGE_ADD, messages)
                resolve()
            })
        },
        [MESSAGE_ADDS]({commit}, messages) {
            return new Promise((resolve, reject) => {
                commit(MESSAGE_ADDS, messages)
                resolve()
            })
        },
        [MESSAGE_DEL]({commit}, id) {
            return new Promise((resolve, reject) => {
                commit(MESSAGE_DEL, id)
                resolve()
            })
        },
    },
}