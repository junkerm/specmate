import { ActivatedRouteSnapshot, NavigationExtras, Params, UrlSegment } from '@angular/router';
import { Config } from '../config/config';
import { UserToken } from '../modules/views/main/authentication/base/user-token';
import { Strings } from './strings';

export class Url {

    public static SEP = '/';

    public static basePath(cls: { className: string }): string {
        return Config.VIEW_URL_PREFIX + cls.className;
    }

    public static decode(path: string): string {
        return decodeURIComponent(path);
    }

    public static getNavigationExtrasRedirect(url: string): NavigationExtras {
        let extras: NavigationExtras = undefined;
        const currentUrl = Url.normalizeForRedirect(url);
        if (currentUrl === '') {
            return {};
        }
        return { queryParams: { r: currentUrl } };
    }

    private static normalizeForRedirect(url: string): string {
        let normalized = url.replace(Config.LOGIN_URL, '').replace(Config.WELCOME_URL, '');
        if (normalized === '/') {
            return '';
        }
        return normalized;
    }

    public static stripBasePath(path: string): string {
        // Expected input: /-/basepath/url%2Fmorestuff
        // Output: url/morestuff
        if (path === undefined) {
            return path;
        }
        path = Url.decode(path);
        path = path.slice(Config.VIEW_URL_PREFIX.length);
        return path.slice(path.indexOf(this.SEP, path.indexOf(this.SEP) + 1) + 1);
    }

    public static project(url: string): string {
        if (url === undefined || url === null || url === '') {
            return undefined;
        }
        const decodedUrl = Url.decode(url);
        let index = 0;
        if (decodedUrl.indexOf(Config.VIEW_URL_PREFIX) === 0) {
            index = 2;
        }
        return Url.parts(decodedUrl)[index];
    }

    public static parent(url: string): string {
        if (url === undefined) {
            return undefined;
        }
        let parts: string[] = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        let parentUrl: string = Url.build(parts);
        if (parentUrl.length === 0) {
            parentUrl = undefined;
        }
        return parentUrl;
    }

    public static isRoot(url: string, project: string): boolean {
        return project === url;
    }

    public static isParent(parentUrl: string, childUrl: string): boolean {
        return Strings.contains(childUrl, parentUrl) && childUrl !== parentUrl;
    }

    public static build(parts: string[], preventCache?: boolean): string {
        if (parts === undefined) {
            return undefined;
        }
        if (parts.filter((part: string) => part === undefined).length > 0) {
            console.error('Supplied undefined part for URL building!');
            console.error(parts);
        }
        let joined: string = parts.join(Url.SEP);
        let url: string = Url.clean(joined);
        if (preventCache) {
            url += '?' + (new Date()).getTime();
        }
        return url;
    }

    public static parts(url: string): string[] {
        if (url) {
            return url.split(Url.SEP);
        }
        return null;
    }

    public static clean(url: string) {
        while (url.indexOf(Url.SEP + Url.SEP) >= 0) {
            url = url.replace(Url.SEP + Url.SEP, Url.SEP);
        }
        if (url.startsWith(Url.SEP)) {
            url = url.slice(1, url.length);
        }
        return url;
    }

    public static fromParams(params: Params): string {
        return Url.decode(params['url']);
    }

    public static fromRoute(route: ActivatedRouteSnapshot): string {
        return route.url.map((urlSegment: UrlSegment) => urlSegment.path).join(Url.SEP);
    }

    static batchOperationUrl(token: UserToken): any {
        return Url.build([Config.URL_BASE, token.project, Config.URL_BATCH]);
    }

    public static urlCreate(url: string): string {
        return Url.build([Config.URL_BASE, Url.parent(Url.decode(url)), Config.URL_CONTENTS]);
    }

    public static urlDelete(url: string): string {
        return Url.build([Config.URL_BASE, Url.decode(url), Config.URL_DELETE]);
    }

    public static urlUpdate(url: string): string {
        return Url.build([Config.URL_BASE, Url.decode(url), Config.URL_ELEMENT]);
    }

    public static urlElement(url: string): string {
        return Url.build([Config.URL_BASE, Url.decode(url), Config.URL_ELEMENT], true);
    }

    public static urlContents(url: string): string {
        return Url.build([Config.URL_BASE, Url.decode(url), Config.URL_CONTENTS], true);
    }

    public static urlCustomService(url: string, serviceName: string): string {
        return Url.build([Config.URL_BASE, Url.decode(url), serviceName], true);
    }

    public static urlCheckConnectivity(project: string): string {
        return Url.build([Config.URL_BASE, project, 'list'], true);
    }

    public static urlAuthenticate(): string {
        return Url.build([Config.URL_BASE, 'login'], true);
    }

    public static urlDeauthenticate(): string {
        return Url.build([Config.URL_BASE, 'logout']);
    }

    public static urlProjectNames(): string {
        return Url.build([Config.URL_BASE, 'projectnames']);
    }
}
