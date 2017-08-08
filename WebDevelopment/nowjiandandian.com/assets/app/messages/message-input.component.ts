import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";

import { MessageService } from "./message.service";
import { Message } from "./message.model";

@Component({
    selector: 'app-message-input',
    templateUrl: './message-input.component.html'
})
export class MessageInputComponent {
    message: Message;

    constructor(private messageService: MessageService) {}

    onSubmit(form: NgForm) {
        // Create
        var image = form.value.image;
        if(image == null  || image.length < 20 || image.includes('instagram') || !(image.includes("http") && image.includes("com")) ){
            form.value.image = "http://shaowenstudio.com/images/defaultPic.png";
        }
        if(form.value.author == null){
            form.value.author = "吃饭要吃饱，否则会做饿梦的";
        }
        this.messageService.addMessage(new Message(
                                        form.value.content, 
                                        form.value.author,
                                        form.value.image,
                                        (new Date()).toLocaleString() ))
            .subscribe(
                data => console.log(data),
                error => console.error(error)
            );
        form.resetForm();
    }

    onClear(form: NgForm) {
        this.message = null;
        form.resetForm();
    }

}