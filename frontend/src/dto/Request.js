export class LoginRequest {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }

    get param() {
        return {
            "email": this.email,
            "password": this.password,
        };
    }

    static of(email, password) {return new this(email, password);}
}