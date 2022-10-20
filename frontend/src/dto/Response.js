import errorCode from "@/utils/errorCode";

export class ErrResponse {
    constructor(response) {
        this.response = response;
    }

    get status() {
        return this.response.response.data.status;
    }

    isIt(code) {return errorCode(this.status, code);}

    static of(response) {return new this(response);}
}

export class LoginResponse {
    constructor(response) {
        this.response = response;
    }

    get token() {
        if(this.response) {
            return this.response.data.data.token;
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class ImageCreateResponse {
    constructor(response) {
        this.response = response;
    }

    get id() {
        if(this.response) {
            return this.response.data.data.id;
        } else {
            return null;
        }
    }
    get url() {
        if(this.response) {
            return this.response.data.data.path;
        } else {
            return null;
        }
    }

    static of() {return new this(null);}
    static of(response) {return new this(response);}
}

export class AccountKeywordResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {uid: item['id'], writer: item['nickName'],}
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class HashtagKeywordResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {tag: item['name'],}
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}
