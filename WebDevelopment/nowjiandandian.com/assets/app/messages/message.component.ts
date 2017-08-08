import { Component, Input, OnInit } from "@angular/core";

import { Message } from "./message.model";
import { MessageService } from "./message.service";

@Component({
    selector: 'app-message',
    templateUrl: './message.component.html',
    styles: [`
        .author {
            display: inline-block;
            font-style: italic;
            font-size: 12px;
            width: 80%;
        }
        .config {
            display: inline-block;
            text-align: right;
            font-size: 12px;
            width: 19%;
        }
    `]
})
export class MessageComponent implements OnInit{
    @Input() message: Message;
    // outputContent: string = this.message.content;

    constructor(private messageService: MessageService) {}

    ngOnInit() {
        console.log(this.message.content)
    }

    // onDelete() {
    //     this.messageService.deleteMessage(this.message)
    //         .subscribe(
    //             result => console.log(result)
    //         );
    // }
}