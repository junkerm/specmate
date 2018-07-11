import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlSegment } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../../auth/services/authentication.service';
import { Injectable } from '@angular/core';
import { Config } from '../../../../../../../config/config';

@Injectable()
export class UserPermissionsGuard implements CanActivate {

    constructor(private auth: AuthenticationService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        if (!this.auth.isAuthenticated) {
            this.auth.redirect = route.url.map((urlSegment: UrlSegment) => urlSegment.path);
            this.router.navigate([Config.LOGIN_URL], { skipLocationChange: true });
        }
        return this.auth.isAuthenticated;
    }
}
