import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, NavigationExtras } from '@angular/router';
import { Location } from '@angular/common';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../services/authentication.service';
import { Injectable } from '@angular/core';
import { Config } from '../../../../../../../config/config';
import { Url } from '../../../../../../../util/url';

@Injectable()
export class UserPermissionsGuard implements CanActivate {

    constructor(private auth: AuthenticationService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        const url = route.url.map(segment => segment.path).join(Url.SEP);
        const isAuthenticated = this.auth.isAuthenticatedForUrl(url);
        if (!isAuthenticated) {
            this.router.navigate([Config.LOGIN_URL], Url.getNavigationExtrasRedirect(state.url));
        }
        return this.auth.isAuthenticated;
    }
}
