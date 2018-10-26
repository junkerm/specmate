import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';
import { User } from '../../../../../../../model/User';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import {Observable} from 'rxjs';
import { stringify } from 'querystring';
import { ThrowStmt } from '@angular/compiler';

@Component({
    moduleId: module.id.toString(),
    selector: 'login',
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})

export class Login implements OnInit, AfterViewInit {
    public username = '';
    public password = '';
    public _project = '';
    public projectnames: string[];
    public body: any;
    public bgImage = 'https://www.backgroundcheckadvantage.com/userfiles/com.background/image/bca-slide___Source.jpg';
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
     name = 'Angular 4';
  urls: string[]  = new Array(10);
selectedFile: File;
    onSelectFile(event: { target: { files: Blob[]; }; }) {
     this.selectedFile = <File>event.target.files[0];
        if (event.target.files && event.target.files[0]) {
          let reader = new FileReader();
    reader.readAsDataURL(this.selectedFile); // read file as data url
    reader.onload = (event) => { // called once readAsDataURL is completed
        this.urls.push(<string>reader.result);
        sessionStorage.clear();
        sessionStorage.setItem('urls' , JSON.stringify(this.urls));
        console.log('added new img');
        console.log(this.urls);
        this.body = document.getElementsByTagName('body')[0];
       if ( this.body ) {
            this.body.style.backgroundImage = "url('" + reader.result + "')";
            // this.body.style.backgroundRepeat = 'no-repeat' ;
        }
          };
        }
      }
      setBGImage(url: string) {
        this.body = document.getElementsByTagName('body')[0];
        if ( this.body ) {
             this.body.style.backgroundImage = "url('" + url + "')";
             // this.body.style.backgroundRepeat = 'no-repeat' ;
         }
      }
    setBackGround() {
        console.log('background setting....');
console.log(this.urls.length);
        if ( this.urls.length != 0 ) {
             let index: number = this.getRndInteger(0, this.urls.length - 1);
             this.body = document.getElementsByTagName('body')[0];
            console.log(index);
            console.log(this.urls[index]);
         if ( this.body && (this.urls[index] != undefined || this.urls[index] != null)) {
            this.body.style.backgroundImage = "url('" + this.urls[index] + "')";
            // this.body.style.backgroundRepeat = 'no-repeat' ;
         } else if (this.body) {
            this.body.style.backgroundImage = "url('" + this.bgImage + "')";
            // this.body.style.backgroundRepeat = 'no-repeat' ;
         }
        }
    }
     getRndInteger(min: number, max: number) {
        return Math.floor(Math.random() * (max - min + 1) ) + min;
    }
    ngAfterViewInit(): void {
        let urlStr: string = sessionStorage.getItem('urls');
        if (urlStr != null || urlStr != undefined) {
            console.log(urlStr);
        this.urls = JSON.parse(JSON.parse(JSON.stringify(urlStr)));
        this.setBackGround();
        } else {
        this.body = document.getElementsByTagName('body')[0];
       if ( this.body ) {
            this.body.style.backgroundImage = "url('" + this.bgImage + "')";
        }
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
