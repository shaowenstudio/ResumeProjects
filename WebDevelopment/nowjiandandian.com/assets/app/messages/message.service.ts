import { Http, Response, Headers } from "@angular/http";
import { Injectable, EventEmitter } from "@angular/core";
import 'rxjs/Rx';
import { Observable } from "rxjs";

import { Message } from "./message.model";
import { Comment } from "../comments/comment.model";

@Injectable()
export class MessageService {
    private messages: Message[] = [];
    private comments: Comment[] = [];

    constructor(private http: Http) {}

    addMessage(message: Message) {
        const body = JSON.stringify(message);
        const headers = new Headers({'Content-Type': 'application/json'});
        return this.http.post('http://localhost:3000/message', body, {headers: headers})
            .map((response: Response) => { // chained method
                const result = response.json();
                const message = new Message(
                    result.obj.content, 
                    result.obj.author, 
                    result.obj.image, 
                    result.obj.created,
                    result.obj._id 
                );
                this.messages.push(message);
                return message;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    getMessages() {
        return this.http.get('http://localhost:3000/message')
            .map((response: Response) => {
                const messages = response.json().obj;
                let transformedMessages: Message[] = [];
                for (let message of messages) {
                    transformedMessages.push(new Message(
                        message.content,   
                        message.author,   
                        message.image, 
                        message.created,   
                        message._id
                    ));
                }
                this.messages = transformedMessages;
                return transformedMessages;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    deleteMessage(message: Message) {
        this.messages.splice(this.messages.indexOf(message), 1);
        return this.http.delete('http://localhost:3000/message/' + message.messageId)
            .map((response: Response) => response.json())
            .catch((error: Response) => Observable.throw(error.json()));
    }

    findMessageById(message_id: string){
        for (let message of this.messages){
            if(message.messageId === message_id){
                return message;
            }
        }
    }

    addComment(comment: Comment) {
        const body = JSON.stringify(comment);
        const headers = new Headers({'Content-Type': 'application/json'});
        return this.http.post('http://localhost:3000/message/' + comment.message, body, {headers: headers})
            .map((response: Response) => { // chained method
                const result = response.json();
                const comment = new Comment(
                    result.obj.inputComment, 
                    result.obj.created,
                    result.obj.theMessage._id,
                    result.obj._id 
                );
                // this.comments.push(comment);
                return comment;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    getComments(message: Message) {
        return this.http.get('http://localhost:3000/message/' + message.messageId)
            .map((response: Response) => {
                const comments = response.json().obj;
                let transformedComments: Comment[] = [];
                for (let comment of comments) {
                    transformedComments.push(new Comment(
                        comment.inputContent, 
                        comment.created,
                        comment.theMessage,
                        comment._id
                    ));
                }
                this.comments = transformedComments;
                return transformedComments;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    // deleteComment(comment: Comment) {
    //     const body = JSON.stringify(comment);
    //     console.log('body: ' + body);
    //     this.comments.splice(this.comments.indexOf(comment), 1);
    //     return this.http.delete('http://localhost:3000/message/' + comment.message, body)
    //         .map((response: Response) => response.json())
    //         .catch((error: Response) => Observable.throw(error.json()));
    // }
}