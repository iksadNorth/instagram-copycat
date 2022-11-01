export class Message {
    constructor(cid, nickname) {
        this.cid = cid;
        this.nickname = nickname;
    }

    get content() {
        return {
            cid: this.cid,
            nickname: this.nickname,
        }
    } 

    static of(cid, nickname) {
        return new Message(cid, nickname);
    }

    static empty() {
        return new Message(null, null);
    }
}
