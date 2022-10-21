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

export class AccountKeyWordRequest {
    constructor(name) {
        this.name = name;
    }

    get param() {
        return {
            "nickName": this.name,
        };
    }

    static of(name) {return new this(name);}
}

export class HashtagKeyWordRequest {
    constructor(name) {
        this.name = name;
    }

    get param() {
        return {
            "name": this.name,
        };
    }

    static of(name) {return new this(name);}
}

// https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting.sorting
// https://www.bezkoder.com/spring-data-sort-multiple-columns/
export class PageRequest {
    constructor(page, size, sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        // sort: {col: 칼럼명, asc: true | false}
    }

    With(obj) {
        const page = {};
        if(this.page) {page = Object.assign(page, {page: this.page,})}
        if(this.size) {page = Object.assign(page, {size: this.size,})}
        if(this.sort) {page = Object.assign(page, {sort: `${this.sort.col},${(this.sort.asc) ? "asc" : "desc"}`,})}
        return Object.assign(obj, page);
    }

    static of(page, size, sort) {return new this(page, size, sort);}
    static of(page, size) {return new this(page, size, null);}
}
