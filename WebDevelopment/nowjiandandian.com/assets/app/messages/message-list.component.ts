import { Component, OnInit} from "@angular/core";

import { Message } from "./message.model";
import { MessageService } from "./message.service";

@Component({
    selector: 'app-message-list',
    template: `
        <app-message
            [message]="message"
            *ngFor="let message of messages">
        </app-message>
    `
})

export class MessageListComponent implements OnInit {
    messages: Message[];

    constructor(private messageService: MessageService) {}

    ngOnInit() {
        this.messageService.getMessages()
            .subscribe(
                (messages: Message[]) => {
                    this.messages = messages.reverse();
                }
            );
    }
}