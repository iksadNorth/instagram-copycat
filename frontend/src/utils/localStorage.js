export function saveAccessToken(token) {
    localStorage.setItem('accessToken',token);
}

export function loadAccessToken() {
    return localStorage.getItem('accessToken');
}

export function DelAccessToken() {
    return localStorage.removeItem('accessToken');
}
