import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";

import { Comment } from "../comments/comment.model";
import { MessageService } from "./message.service";

@Component({
    selector: 'app-message-detail',
    templateUrl: './message-detail.component.html',
})
export class MessageDetailComponent implements OnInit{
    id = document.URL.substring(document.URL.indexOf('messages/') + 9);
    thisCommentAddr = '/messages/' + this.id + '/newComment';
    message = this.messageService.findMessageById(this.id);
    comments: Comment[] = [];

    constructor(private messageService: MessageService) {}

    onSubmit(form: NgForm) {      // Create
        this.comments.push(new Comment(  // temporary storage, display on web
                                form.value.inputComment, 
                                (new Date()).toLocaleString(),
                                this.message.messageId ));

        // save comment to message
        this.messageService.addComment(new Comment(
                                form.value.inputComment, 
                                (new Date()).toLocaleString(),
                                this.message.messageId ))
            .subscribe(
                data => console.log("data: " + data),
                error => console.error(error)
            );
                
        form.resetForm();
    }

    onDelete() {
        var comCode = prompt("Enter 511 to finish delet", "511留数删字");

        if (comCode == '511') {
            this.messageService.deleteMessage(this.message)
                .subscribe(
                    result => console.log(result)
                );
            alert('Message deleted. Wait few seconds to processing.');
        } else {
            alert('Invalid comformation code');
            console.log('Invalid comformation code');
        }        
    }

    // onDeleteComment(comment: Comment) {
    //     console.log('comment: ' + comment);
    //     this.messageService.deleteComment(comment)
    //         .subscribe(
    //             result => console.log(result)
    //         );
    // }

    ngOnInit() {
        this.messageService.getComments(this.message)
            .subscribe(
                (comments: Comment[]) => {
                    this.comments = comments;
                }
            );
    }
}