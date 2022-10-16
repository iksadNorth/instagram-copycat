export class Claim {
    
    constructor(email, exp, iat) {
        this.email = email;
        this.exp = exp;
        this.iat = iat;
    }

    get account() {
        return {
            "uid": 1,
            "email": this.email,
        };
    }

    static of(jwtDec) {return new this(jwtDec.email, jwtDec.exp, jwtDec.iat);}
    static of(email, exp, iat) {return new this(email, exp, iat);}
}