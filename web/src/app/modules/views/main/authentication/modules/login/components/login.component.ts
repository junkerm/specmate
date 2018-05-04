import { Component } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'login',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class Login {
    public username: string;
    public password: string;
    public project: string;

    constructor(private auth: AuthenticationService) { }

    public async authenticate(): Promise<boolean> {
        if (!this.canLogin) {
            return Promise.resolve(false);
        }
        await this.auth.authenticate(this.username, this.password, this.project);
        return Promise.resolve(this.auth.isAuthenticated);
    }

    public get canLogin(): boolean {
        return this.isFilled(this.username) && this.isFilled(this.password) && this.isFilled(this.project);
    }

    private isFilled(str: string): boolean {
        return str !== undefined && str !== null && str.length > 0 &&  str !== '';
    }
}
