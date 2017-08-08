import { Component } from "@angular/core";

@Component({
    selector: 'app-header',
    template: `
        <ul class="nav nav-tabs">
            <a [routerLink]="['/messages']" class="navbar-brand"   style="color: black">
                <i class="fa fa-superpowers" aria-hidden="true"></i> EveryFirstTime
            </a>
            <li role="presentation" routerLinkActive="active" style="font-size: 1.1em;">
                <a [routerLink]="['/messages']">All Messages</a>
            </li>
            <li role="presentation" routerLinkActive="active" style="font-size: 1.1em;">
                <a [routerLink]="['/auth']">Transaction</a>
            </li>
        </ul>
    `
})
export class HeaderComponent {

}

