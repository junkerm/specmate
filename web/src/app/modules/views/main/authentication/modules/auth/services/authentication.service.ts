import { Injectable, EventEmitter } from '@angular/core';
import { UserToken } from '../../../base/user-token';
import { HttpClient } from '@angular/common/http';
import { ServiceInterface } from '../../../../../../data/modules/data-service/services/service-interface';
import { Router, UrlSegment, NavigationEnd, GuardsCheckEnd, ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { Config } from '../../../../../../../config/config';
import { IContainer } from '../../../../../../../model/IContainer';
import { LoggingService } from '../../../../../side/modules/log-list/services/logging.service';
import { TranslateService } from '@ngx-translate/core';
import { Url } from '../../../../../../../util/url';
import { CookieService } from 'ngx-cookie';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { User } from '../../../../../../../model/User';
import { Location } from '@angular/common';

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

    private _redirect: string[];
    public get redirect(): string[] {
        return this._redirect;
    }
    public set redirect(redirect: string[]) {
        this._redirect = redirect;
    }

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

    constructor(private http: HttpClient,
        private router: Router,
        private location: Location,
        private logger: LoggingService,
        private translate: TranslateService,
        private cookie: CookieService) {

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
                if (Url.isParent(this.token.project, Url.build(this.redirect))) {
                    this.router.navigate(this.redirectUrlSegments, { skipLocationChange: true });
                } else {
                    this.redirect = undefined;
                }
                if (this.location.path().endsWith(Config.LOGIN_URL)) {
                    this.location.replaceState(Url.SEP);
                }
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

    private clearToken(): void {
        this.token = UserToken.INVALID;
    }

    private storeTokenInCookie(token: UserToken): void {
        this.cookie.put(
            AuthenticationService.TOKEN_COOKIE_KEY,
            JSON.stringify(token));
    }

    public async deauthenticate(omitServer?: boolean): Promise<void> {
        try {
            const subscription = this.router.events.subscribe(async event => {
                if (event instanceof GuardsCheckEnd) {
                    if (event.shouldActivate) {
                        await this.doDeauth(omitServer);
                        subscription.unsubscribe();
                    }
                }
            });
            await this.router.navigate([Config.LOGIN_URL], { skipLocationChange: true });

        } catch (e) {
            this.logger.error(this.translate.instant('logoutFailed'));
        }
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

    private get redirectUrlSegments(): string[] {
        if (!this.redirect || this.redirect.length === 0) {
            return [Url.SEP];
        }
        return this.redirect;
    }
}
