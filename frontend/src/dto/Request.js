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

export class AccountCreateRequest {
    constructor(email, userName, nickName, password, dateOfBirth) {
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    get param() {
        return {
            "email": this.email,
            "userName": this.userName,
            "nickName": this.nickName,
            "password": this.password,
            "dateOfBirth": this.dateOfBirth,
        };
    }

    static of() {return new this(undefined, undefined, undefined, undefined, undefined);}

    overWrite(req) {
        for (const key in req) {
            const newOne = req[key];
            if (newOne != null) {
                Reflect.set(this, key, newOne);
            }
        }
    }
}

export class AccountVerifyRequest {
    constructor(email) {
        this.email = email;
    }

    get param() {
        return {
            "email": this.email,
        };
    }

    static of(email) {return new this(email);}
}