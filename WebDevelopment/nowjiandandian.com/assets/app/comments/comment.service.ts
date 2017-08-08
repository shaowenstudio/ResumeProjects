import { Http, Response, Headers } from "@angular/http";
import { Injectable, EventEmitter } from "@angular/core";
import 'rxjs/Rx';
import { Observable } from "rxjs";
import { MessageService } from "../messages/message.service";

import { Comment } from "./comment.model";

@Injectable()
export class CommentService {
    private comments: Comment[] = [];

    constructor(private http: Http,
                private messageService: MessageService) {}

    addMessage(comment: Comment) {
        const body = JSON.stringify(comment);
        const headers = new Headers({'Content-Type': 'application/json'});
        return this.http.post('http://shaowenstudio.com:3000/:id', body, {headers: headers})
            .map((response: Response) => { // chained method
                const result = response.json();
                const comment = new Comment(
                    result.obj.content, 
                    (new Date()).toLocaleString('en-US', { timeZone: 'America/Chicago' }),
                    result.obj._id 
                );
                this.comments.push(comment);
                return comment;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    getMessages() {
        return this.http.get('http://shaowenstudio.com:3000/:id')
            .map((response: Response) => {
                const comments = response.json().obj;
                let transformedMessages: Comment[] = [];
                for (let comment of comments) {
                    transformedMessages.push(new Comment(
                        comment.content, 
                        comment.created,
                        comment._id
                    ));
                }
                this.comments = transformedMessages;
                return transformedMessages;
            })
            .catch((error: Response) => Observable.throw(error.json()));
    }

    deleteMessage(comment: Comment) {
        this.comments.splice(this.comments.indexOf(comment), 1);
        return this.http.delete('http://shaowenstudio.com:3000/comment/' + comment.messageId)
            .map((response: Response) => response.json())
            .catch((error: Response) => Observable.throw(error.json()));
    }

    findMessageById(message_id: string){
        this.messageService.findMessageById(message_id);
    }
}