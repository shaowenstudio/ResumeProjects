import { Component } from "@angular/core";

@Component({
    selector: 'app-authentication',
    template: `
        <header class="row spacing">
            <nav class="col-md-8 col-md-offset-2">
                <ul class="nav nav-tabs">
                    <li routerLinkActive="active"><a [routerLink]="['signup']">所有交易</a></li>
                    <li routerLinkActive="active"><a [routerLink]="['signin']">商品</a></li>
                    <li routerLinkActive="active"><a [routerLink]="['logout']">房子</a></li>
                </ul>
            </nav>
        </header>
        <div class="row spacing">
           <router-outlet></router-outlet>
        </div>
    `
})
export class AuthenticationComponent {

}