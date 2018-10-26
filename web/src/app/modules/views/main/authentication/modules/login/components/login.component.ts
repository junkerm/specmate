import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';
import { User } from '../../../../../../../model/User';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'login',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class Login implements OnInit {
    public username = '';
    public password = '';
    public _project = '';
    public projectnames: string[];
   public body: any;
    public bgImage = 'https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940';


    public isAuthenticating = false;

    constructor(private auth: AuthenticationService, private navigator: NavigatorService) {
      auth.getProjectNames().then(res => this.projectnames = res);
    }

    public get project(): string {
        return this._project;
    }

    public set project(project: string) {
        this._project = project;
    }


    ngOnInit() {
        this.tryNavigateAway();
    }
    ngAfterViewInit(): void {
        this.body = document.getElementsByTagName('body')[0];

        if ( this.body ) {
            this.body.style.backgroundImage = "url('" + this.bgImage + "')";
        }
    }
    private tryNavigateAway(): void {
        if (this.auth.isAuthenticated) {
            this.navigator.navigate('default');
        }
    }

    public async authenticate(): Promise<boolean> {
        if (!this.canLogin) {
            return Promise.resolve(false);
        }
        let user = new User();
        user.userName = this.username;
        user.passWord = this.password;
        user.projectName = this.project;
        this.isAuthenticating = true;
        await this.auth.authenticate(user);
        this.tryNavigateAway();
        this.isAuthenticating = false;
      this.body.style.backgroundImage = null;
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
