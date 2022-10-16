import axios from 'axios';
import { setInterceptors } from '@/utils/interceptors';

function createAxios() {
	const instance = axios.create();
	return instance;
}

function createAxiosWithAuth() {
    const instance = axios.create();
	return setInterceptors(instance);
}

export const instance = createAxios();
export const instanceWithAuth = createAxiosWithAuth();