import { Injectable, EventEmitter } from '@angular/core';
import { UserToken } from '../../../base/user-token';
import { HttpClient } from '@angular/common/http';
import { ServiceInterface } from '../../../../../../data/modules/data-service/services/service-interface';
import { Router, GuardsCheckEnd } from '@angular/router';
import { Config } from '../../../../../../../config/config';
import { LoggingService } from '../../../../../side/modules/log-list/services/logging.service';
import { TranslateService } from '@ngx-translate/core';
import { Url } from '../../../../../../../util/url';
import { CookieService } from 'ngx-cookie';
import { User } from '../../../../../../../model/User';

@Injectable()
export class AuthenticationService {

    private static TOKEN_COOKIE_KEY = 'specmate-user-token';

    public get token(): UserToken {
        const json = this.cookie.get(AuthenticationService.TOKEN_COOKIE_KEY);
        if (json !== undefined) {
            const token: UserToken = JSON.parse(json);
            if (!UserToken.isInvalid(token)) {
                this.cachedToken = token;
            }
            return token;
        }
        return UserToken.INVALID;
    }

    public set token(token: UserToken) {
        if (!UserToken.isInvalid(token)) {
            this.cachedToken = token;
        }
        this.storeTokenInCookie(token);
    }

    private serviceInterface: ServiceInterface;

    private _authChanged: EventEmitter<boolean>;

    private _authFailed: boolean;
    public get authFailed(): boolean {
        return this._authFailed;
    }
    public set authFailed(authFailed: boolean) {
        this._authFailed = authFailed;
    }

    private _inactivityLoggedOut: boolean;
    public get inactivityLoggedOut(): boolean {
        return this._inactivityLoggedOut;
    }
    public set inactivityLoggedOut(inactivityLoggedOut: boolean) {
        this._inactivityLoggedOut = inactivityLoggedOut;
    }

    private _errorLoggedOut: boolean;
    public get errorLoggedOut(): boolean {
        return this._errorLoggedOut;
    }
    public set errorLoggedOut(errorLoggedOut: boolean) {
        this._errorLoggedOut = errorLoggedOut;
    }

    private cachedToken: UserToken;

    constructor(http: HttpClient, private cookie: CookieService) {

        this.serviceInterface = new ServiceInterface(http);
    }

    public get authChanged(): EventEmitter<boolean> {
        if (!this._authChanged) {
            this._authChanged = new EventEmitter<boolean>();
        }
        return this._authChanged;
    }

    public async authenticate(user: User): Promise<UserToken> {
        try {
            const wasAuthenticated: boolean = this.isAuthenticated;
            this.token = await this.serviceInterface.authenticate(user);
            if (this.isAuthenticated) {
                if (wasAuthenticated !== this.isAuthenticated) {
                    this.authChanged.emit(true);
                }
                this.authFailed = false;
                this.inactivityLoggedOut = false;
                this.errorLoggedOut = false;
                return this.token;
            }
        } catch (e) {
            this.authFailed = true;
        }
    }

    public get isAuthenticated(): boolean {
        return !UserToken.isInvalid(this.token);
    }

    public isAuthenticatedForUrl(url: string): boolean {
        if (!this.isAuthenticated) {
            return false;
        }
        if (url === undefined) {
            return false;
        }
        if (url === '' || (Url.SEP + Config.WELCOME_URL).endsWith(url)) {
            return true;
        }
        return this.isAuthenticatedForProject(Url.project(url));
    }

    public isAuthenticatedForProject(project: string): boolean {
        return this.token.project === project;
    }

    private clearToken(): void {
        this.token = UserToken.INVALID;
        this.cookie.remove(AuthenticationService.TOKEN_COOKIE_KEY);
    }

    private storeTokenInCookie(token: UserToken): void {
        this.cookie.put(
            AuthenticationService.TOKEN_COOKIE_KEY,
            JSON.stringify(token));
    }

    public async deauthenticate(omitServer?: boolean): Promise<void> {
        await this.doDeauth(omitServer);
    }

    private async doDeauth(omitServer?: boolean): Promise<void> {
        const wasAuthenticated: boolean = this.isAuthenticated;
        const token = this.token;
        this.clearToken();
        this.authFailed = false;
        if (omitServer !== true) {
            if (UserToken.isInvalid(token)) {
                try {
                    // The cached token should never be invalid. If it is, we want to deuath prior to auth.
                    this.serviceInterface.deauthenticate(this.cachedToken);
                    this.cachedToken = undefined;
                } catch (e) {
                    // We silently ignore errors on invalidating cached tokens,
                    // as this should not be relevant for security,
                    // just for cleanliness.
                }
            } else {
                await this.serviceInterface.deauthenticate(token);
                this.cachedToken = undefined;
            }
        }
        if (wasAuthenticated !== this.isAuthenticated) {
            this.authChanged.emit(false);
        }
    }

    public async getProjectNames(): Promise<string[]> {
        return await this.serviceInterface.projectnames();
    }
}
