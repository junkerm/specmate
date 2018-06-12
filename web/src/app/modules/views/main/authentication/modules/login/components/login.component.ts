import { Component } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';
import { User } from '../../../../../../../model/User';

@Component({
    moduleId: module.id.toString(),
    selector: 'login',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class Login {
    public username = 'The username';
    public password = 'The secret password';
    public project = '';
    public projectnames: string[];

    constructor(private auth: AuthenticationService) {
      auth.getProjectNames().then(res => this.projectnames = res);
    }

    public async authenticate(): Promise<boolean> {
        if (!this.canLogin) {
            return Promise.resolve(false);
        }
        let user = new User();
        user.userName = this.username;
        user.passWord = this.password;
        user.projectName = this.project;
        await this.auth.authenticate(user);
        return Promise.resolve(this.auth.isAuthenticated);
    }

    public get canLogin(): boolean {
        return this.isFilled(this.username) && this.isFilled(this.password) && this.isFilled(this.project);
    }

    private isFilled(str: string): boolean {
        return str !== undefined && str !== null && str.length > 0 &&  str !== '';
    }

    public get isLoginFailed(): boolean {
        return this.auth.authFailed;
    }

    public get isInactivityLoggedOut(): boolean {
        return this.auth.inactivityLoggedOut;
    }
    public get isErrorLoggedOut(): boolean {
        return this.auth.errorLoggedOut;
    }
}
