import { Injectable, EventEmitter } from '@angular/core';
import { UserToken } from '../../../base/user-token';
import { HttpClient } from '@angular/common/http';
import { ServiceInterface } from '../../../../../../data/modules/data-service/services/service-interface';
import { Router, UrlSegment } from '@angular/router';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { Config } from '../../../../../../../config/config';
import { IContainer } from '../../../../../../../model/IContainer';
import { LoggingService } from '../../../../../side/modules/log-list/services/logging.service';
import { TranslateService } from '@ngx-translate/core';
import { Url } from '../../../../../../../util/url';
import { CookieStorage } from 'ngx-store';

@Injectable()
export class AuthenticationService {

    @CookieStorage({key: 'specmate-user-token', expires: new Date(new Date().getTime() + 24 * 60 * 60 * 1000)})
    public token: UserToken = UserToken.INVALID;
    private serviceInterface: ServiceInterface;
    public redirect: string[];

    private _authChanged: EventEmitter<boolean>;

    public authFailed: boolean;
    public inactivityLoggedOut: boolean;

    constructor(private http: HttpClient,
        private router: Router,
        private logger: LoggingService,
        private translate: TranslateService) {

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
                this.authFailed = false;
                this.inactivityLoggedOut = false;
                return this.token;
            }
        } catch (e) {
            this.authFailed = true;
        }
    }

    public get isAuthenticated(): boolean {
        return !UserToken.isInvalid(this.token);
    }

    private async clearToken(): Promise<void> {
        this.token = UserToken.INVALID;
        while (!UserToken.isInvalid(this.token)) {
            await new Promise(res => setTimeout(() => res(), 100));
        }
    }

    public async deauthenticate(omitServer?: boolean): Promise<void> {
        const wasAuthenticated: boolean = this.isAuthenticated;
        try {
            if (omitServer !== true) {
                await this.serviceInterface.deauthenticate(this.token);
            }
            await this.clearToken();
            this.authFailed = false;
            this.redirect = undefined;
            await this.router.navigate([Config.LOGIN_URL]);
            if (wasAuthenticated !== this.isAuthenticated) {
                this.authChanged.emit(false);
            }
        } catch (e) {
            this.logger.error(this.translate.instant('logoutFailed'));
        }
    }

    private get redirectUrlSegments(): string[] {
        if (!this.redirect || this.redirect.length === 0) {
            return ['/'];
        }
        return this.redirect;
    }
}
