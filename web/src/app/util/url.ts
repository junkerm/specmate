import { Config } from '../config/config';
import { Strings } from './strings';
import { Params, Router, UrlSegment, ActivatedRouteSnapshot } from '@angular/router';
import { UserToken } from '../modules/views/main/authentication/base/user-token';

export class Url {

    public static SEP = '/';

    public static basePath(cls: { className: string }): string {
        return Config.VIEW_URL_PREFIX + cls.className;
    }

    public static stripBasePath(path: string): string {
        // Expected input: /-/basepath/url%2Fmorestuff
        // Output: url/morestuff
        path = decodeURIComponent(path);
        path = path.slice(Config.VIEW_URL_PREFIX.length);
        return path.slice(path.indexOf(this.SEP, path.indexOf(this.SEP) + 1) + 1);
    }

    public static parent(url: string): string {
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
        return params['url'];
    }

    public static fromRoute(route: ActivatedRouteSnapshot): string {
        return route.url.map((urlSegment: UrlSegment) => urlSegment.path).join(Url.SEP);
    }

    static batchOperationUrl(token: UserToken): any {
        return Url.build([Config.URL_BASE, token.project, Config.URL_BATCH]);
    }

    public static urlCreate(url: string): string {
        return Url.build([Config.URL_BASE, Url.parent(url), Config.URL_CONTENTS]);
    }

    public static urlDelete(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_DELETE]);
    }

    public static urlUpdate(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_ELEMENT]);
    }

    public static urlElement(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_ELEMENT], true);
    }

    public static urlContents(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_CONTENTS], true);
    }

    public static urlCustomService(url: string, serviceName: string): string {
        return Url.build([Config.URL_BASE, url, serviceName], true);
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
