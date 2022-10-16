const jwt = require("jsonwebtoken");

export function jwtDec(token) {
    return jwt.decode(token);
}