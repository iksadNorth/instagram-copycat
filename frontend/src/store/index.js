import { createStore } from 'vuex';

export default createStore({
	state: {
		emailsignup: {
			screenState: "joinform", 
            // screenState: "birthform",
            // screenState: "vertificationform",
            // screenState: "termform",
		}
	},
	getters: {
	},
	mutations: {
		setScreenState: (state, payload) => {
			state.emailsignup.screenState = payload;
		}
	},
	actions: {
	}
});
