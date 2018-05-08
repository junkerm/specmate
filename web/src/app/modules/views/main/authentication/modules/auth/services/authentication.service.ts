import { Injectable, EventEmitter } from '@angular/core';
import { UserToken } from '../../../base/user-token';
import { HttpClient } from '@angular/common/http';
import { ServiceInterface } from '../../../../../../data/modules/data-service/services/service-interface';
import { Router, UrlSegment } from '@angular/router';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { Config } from '../../../../../../../config/config';
import { IContainer } from '../../../../../../../model/IContainer';

@Injectable()
export class AuthenticationService {

    public token: UserToken;
    private serviceInterface: ServiceInterface;
    public redirect: UrlSegment[];

    private _authChanged: EventEmitter<boolean>;

    constructor(private http: HttpClient, private router: Router) {
        this.serviceInterface = new ServiceInterface(http);
    }

    public get authChanged(): EventEmitter<boolean> {
        if (!this._authChanged) {
            this._authChanged = new EventEmitter<boolean>();
        }
        return this._authChanged;
    }

    public async authenticate(user: string, password: string, project: string): Promise<UserToken> {
        try {
            const wasAuthenticated: boolean = this.isAuthenticated;
            this.token = await this.serviceInterface.authenticate(user, password, project);
            if (this.isAuthenticated) {
                this.router.navigate(this.redirectUrlSegments);
                if (wasAuthenticated !== this.isAuthenticated) {
                    this.authChanged.emit(true);
                }
                return this.token;
            }
        } catch (e) {
            console.error(e);
            throw new Error('TODO: IMPLEMENT: LOGIN FAILED');
        }
    }

    public get isAuthenticated(): boolean {
        return this.token !== undefined;
    }

    public async deauthenticate(): Promise<void> {
        const wasAuthenticated: boolean = this.isAuthenticated;
        this.token = undefined;
        if (wasAuthenticated !== this.isAuthenticated) {
            this._authChanged.emit(false);
        }
        return this.serviceInterface.deauthenticate();
    }

    private get redirectUrlSegments(): string[] {
        if (!this.redirect || this.redirect.length === 0) {
            return ['/'];
        }
        return this.redirect.map((urlSegment: UrlSegment) => urlSegment.path);
    }
}
