import { Component } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'logout',
    templateUrl: 'logout.component.html',
    styleUrls: ['logout.component.css']
})
export class Logout {
    constructor(private auth: AuthenticationService) { }

    public logout(): void {
        this.auth.deauthenticate();
    }
}
