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

export class ArticleCreateRequest {
    constructor(id, url, content, toggleHide, toggleComment) {
        this.id = id;
        this.url = url;
        this.content = content;
        this.toggleHide = toggleHide;
        this.toggleComment = toggleComment;
    }

    get param() {
        return {
            "image": {"id": this.id, "path": this.url,},
            "content": this.content,
            "isHideLikesAndViews": this.toggleHide,
            "isAllowedComments": this.toggleComment,
        };
    }

    static of(id, url, content, toggleHide, toggleComment) {return new this(id, url, content, toggleHide, toggleComment);}
}
