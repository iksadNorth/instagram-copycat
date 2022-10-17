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
        return this.response.data.data.token;
    }

    static of(response) {return new this(response);}
}

export class ImageCreateResponse {
    constructor(response) {
        this.response = response;
    }

    get url() {
        return this.response.data.data.url;
    }

    static of(response) {return new this(response);}
}
