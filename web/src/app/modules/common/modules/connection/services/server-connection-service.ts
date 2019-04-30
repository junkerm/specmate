import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs/Rx';
import { Config } from '../../../../../config/config';
import { ServiceInterface } from '../../../../data/modules/data-service/services/service-interface';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';

@Injectable()
export class ServerConnectionService {

    private serviceInterface: ServiceInterface;

    public isConnected = true;

    private timer: Observable<number>;

    constructor(
        private auth: AuthenticationService,
        private logger: LoggingService,
        private translate: TranslateService,
        private http: HttpClient) {

        this.serviceInterface = new ServiceInterface(http);
        this.initTime();
    }

    public reset(): void {
        this.initTime();
    }

    private initTime(): void {
        this.timer = Observable.timer(0, Config.CONNECTIVITY_CHECK_DELAY);
        this.timer.subscribe(async () => {
            this.checkConnection();
        });
    }

    private async checkConnection(): Promise<void> {
        try {
            if (this.auth.isAuthenticated) {
                await this.serviceInterface.checkConnection(this.auth.token);
                this.isConnected = true;
            }
        } catch (e) {
            this.handleErrorResponse(e);
            this.isConnected = false;
        }
    }

    public async handleErrorResponse(error: HttpErrorResponse, url?: string): Promise<void> {
        if (error.status === 0) {
            console.log(this.translate.instant('connectionLost'));
            this.logger.error(this.translate.instant('connectionLost'), undefined);
        } else if (error.status === 401) {
            // We were already logged out on the server, so log out just in the UI.
            this.auth.inactivityLoggedOut = true;
            this.auth.deauthenticate(true);
        } else if (error.status === 404) {
            this.logger.error(this.translate.instant('resourceNotFound'), url);
        } else if (error.status === 500) {
            this.logger.error(this.translate.instant('unknownError'), url);
        } else {
            this.auth.errorLoggedOut = true;
            this.auth.deauthenticate();
            console.error(error);
            throw new Error('Request failed with unknown reason!');
        }
    }
}
