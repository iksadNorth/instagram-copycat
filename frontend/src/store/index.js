import { createStore } from 'vuex';

export default createStore({
	state: {
		emailsignup: {
			screenState: "joinform", 
            // screenState: "birthform",
            // screenState: "vertificationform",
            // screenState: "termform",
		},
		account: {
			uid: undefined,
		}
	},
	getters: {
		isLogin(state) {return state.account.uid != null;}
	},
	mutations: {
		setScreenState: (state, payload) => {
			state.emailsignup.screenState = payload;
		}
	},
	actions: {
	}
});
