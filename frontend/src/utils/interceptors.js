import { loadAccessToken } from '@/utils/localStorage';

export function setInterceptors(axios) {
    // 요청 시,
	axios.interceptors.request.use(
		(config) => {
			config.headers.Authorization = loadAccessToken() || "";
			return config;
		},
		(error) => {
			return Promise.reject(error);
		},
	);

    // 응답 시,
	axios.interceptors.response.use(
		(response) => {
			return response;
		},
		(error) => {
			return Promise.reject(error);
		},
	);
	return axios;
}