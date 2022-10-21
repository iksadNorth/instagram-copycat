import { createStore } from 'vuex';
import { AccountCreateRequest } from "@/dto/Request";
import { loadAccessToken } from "@/utils/localStorage"

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
			email: loadAccessToken(),
		},
		account4Creating: AccountCreateRequest.of(),
		dialog: {
			value: false,
		},
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
		setAccount4Creating: (state, payload) => {
			state.account4Creating.overWrite(payload);
		},
		clearAccount4Creating: (state) => {
			state.account4Creating = AccountCreateRequest.of();
		},
		setDialog: (state, payload) => {
			state.dialog.value = payload;
		},
	},
	actions: {
	}
});
