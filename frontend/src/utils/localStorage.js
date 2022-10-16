export function saveAccessToken(token) {
    localStorage.setItem('accessToken',token);
}

export function loadAccessToken() {
    return localStorage.getItem('accessToken');
}