import { Comment } from "../comments/comment.model";

export class Message {
    // content: string;
    // username: string;
    // messageId?: string;
    // userId?: string;

    // constructor(content: string, username: string, messageId?: string, userId?: string) {
    //     this.content = content;
    //     this.username = username;
    //     this.messageId = messageId;
    //     this.userId = userId;
    // }

    constructor(public content: string,
                public author?: string,
                public image?: string,
                public created?: string,
                public messageId?: string,
                public comments?: Comment[]) {}
}