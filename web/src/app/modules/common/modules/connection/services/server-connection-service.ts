import { Injectable } from '@angular/core';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ServiceInterface } from '../../../../data/modules/data-service/services/service-interface';
import { TranslateService } from '@ngx-translate/core';
import { Config } from '../../../../../config/config';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';

@Injectable()
export class ServerConnectionService {

    private serviceInterface: ServiceInterface;

    public isConnected = false;

    constructor(
        private auth: AuthenticationService,
        private logger: LoggingService,
        private translate: TranslateService,
        private http: HttpClient) {

        this.serviceInterface = new ServiceInterface(http);

        let timer = Observable.timer(0, Config.CONNECTIVITY_CHECK_DELAY);
        timer.subscribe(() => {
            this.checkConnection()
                .then(() => this.isConnected = true)
                .catch(() => this.isConnected = false);
        });
    }

    public checkConnection(): Promise<void> {
        return this.serviceInterface.checkConnection(this.auth.token).catch((error: HttpErrorResponse) => {
            if (error.status === 0) {
                this.logger.error(this.translate.instant('connectionLost'), undefined);
            } else if (error.status === 401) {
                // We were already logged out on the server, so log out just in the UI.
                this.auth.inactivityLoggedOut = true;
                this.auth.deauthenticate(true);
            }
        });
    }
}
