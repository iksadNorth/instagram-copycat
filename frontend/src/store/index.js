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
			email: undefined,
		}
	},
	getters: {
		isLogin(state) {return state.account.email != null;}
	},
	mutations: {
		setScreenState: (state, payload) => {
			state.emailsignup.screenState = payload;
		},
		overwriteAccount: (state, payload) => {
			Object.assign(state.account, payload);
		},
	},
	actions: {
	}
});
