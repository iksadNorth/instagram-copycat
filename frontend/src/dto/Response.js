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
                return {uid: item['id'], nickname: item['nickName'],}
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

export class AccountReadByIdResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const item = this.response.data.data
            return {
                uid: item['id'],
                name: item['userName'],
                nickname: item['nickName'],
                introduction: item['introduction'],
                articles: item['articles'],
                followers: item['followers'],
                followings: item['followings'],
            };
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class AccountReadResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {
                    uid: item['id'],
                    name: item['userName'],
                    nickname: item['nickName'],
                    introduction: item['introduction'],
                    articles: item['articles'],
                    followers: item['followers'],
                    followings: item['followings'],
                }
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class ArticleReadByIdResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const item = this.response.data.data;
            return {
                pid: item['id'],
                createdAt: item['createdAt'],
                
                uid: item['account']['id'],
                nickname: item['account']['nickName'],
                imgSrc: item['image']['path'],
                content: item['content'],

                isHideLikesAndViews: item['isHideLikesAndViews'],
                isAllowedComments: item['isAllowedComments'],

                comments: item['numComments'],
                likes: item['numLikes'],
                views: item['numViews'],
            };
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class ArticleReadResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {
                    pid: item['id'],
                    createdAt: item['createdAt'],
                    
                    uid: item['account']['id'],
                    nickname: item['account']['nickName'],
                    imgSrc: item['image']['path'],
                    content: item['content'],

                    isHideLikesAndViews: item['isHideLikesAndViews'],
                    isAllowedComments: item['isAllowedComments'],

                    comments: item['numComments'],
                    likes: item['numLikes'],
                    views: item['numViews'],
                }
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class ArticleReadWithoutAccountResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {
                    pid: item['id'],
                    createdAt: item['createdAt'],
                    
                    imgSrc: item['image']['path'],
                    content: item['content'],

                    comments: item['numComments'],
                    likes: item['numLikes'],
                    views: item['numViews'],
                }
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class FollowReadResponse {
    constructor(response) {
        this.response = response;
    }

    get isFollow() {
        if(this.response) {
            return this.response.data.data.isFollow;
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class LikeReadResponse {
    constructor(response) {
        this.response = response;
    }

    get isLike() {
        if(this.response) {
            return this.response.data.data.isLike;
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}

export class CommentReadResponse {
    constructor(response) {
        this.response = response;
    }

    get content() {
        if(this.response) {
            const array = this.response.data.data.content
            return array.map(item => {
                return {
                    cid: item['id'],
                    createdAt: item['createdAt'],
                    
                    uid: item['account']['id'],
                    nickname: item['account']['nickName'],
                    content: item['content'],

                    likes: item['numLikes'],
                    children: item['numChildren'],
                }
            });
        } else {
            return null;
        }
    }

    static of(response) {return new this(response);}
}
