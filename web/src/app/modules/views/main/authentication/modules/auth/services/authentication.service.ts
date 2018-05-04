import { Injectable } from '@angular/core';
import { UserToken } from '../../../base/user-token';
import { HttpClient } from '@angular/common/http';
import { ServiceInterface } from '../../../../../../data/modules/data-service/services/service-interface';
import { Router, UrlSegment } from '@angular/router';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { Config } from '../../../../../../../config/config';

@Injectable()
export class AuthenticationService {
    public token: UserToken;
    private serviceInterface: ServiceInterface;
    public redirect: UrlSegment[];

    constructor(private http: HttpClient, private router: Router) {
        this.serviceInterface = new ServiceInterface(http);
    }

    public async authenticate(user: string, password: string, project: string): Promise<UserToken> {
        this.token = await this.serviceInterface.authenticate(user, password, project);
        this.router.navigate(this.redirectUrlSegments);
        return this.token;
    }

    public get isAuthenticated(): boolean {
        return this.token !== undefined;
    }

    public async deauthenticate(): Promise<void> {
        return this.serviceInterface.deauthenticate();
    }

    private get redirectUrlSegments(): string[] {
        if (!this.redirect || this.redirect.length === 0) {
            return ['/'];
        }
        return this.redirect.map((urlSegment: UrlSegment) => urlSegment.path);
    }
}
