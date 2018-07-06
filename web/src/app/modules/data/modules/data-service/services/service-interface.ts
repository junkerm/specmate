import { HttpClient, HttpResponse, HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Url } from '../../../../../util/url';
import { IContainer } from '../../../../../model/IContainer';
import { Objects } from '../../../../../util/objects';
import { CEGConnection } from '../../../../../model/CEGConnection';
import { Type } from '../../../../../util/type';
import 'rxjs/add/operator/toPromise';
import { UserToken } from '../../../../views/main/authentication/base/user-token';
import { UserSession } from '../../../../../model/UserSession';
import { User } from '../../../../../model/User';

export class ServiceInterface {
    constructor(private http: HttpClient) { }

    public checkConnection(token?: UserToken): Promise<void> {
        if (token === undefined || token === UserToken.INVALID) {
            const error = new HttpErrorResponse({ status: 401 });
            return Promise.reject(error);
        }
        let params = new HttpParams();
        params = params.append('heartbeat', 'true');
        return this.http.get(Url.urlCheckConnectivity(token.project), { headers: this.getAuthHeader(token), params: params })
            .toPromise()
            .then(() => Promise.resolve());
    }

    public async authenticate(user: User): Promise<UserToken> {
        return this.http.post(Url.urlAuthenticate(), user)
        .toPromise()
        .then((session: UserSession) => new UserToken(session, user.projectName));
    }

    public deauthenticate(token: UserToken): Promise<void> {
        return this.http.get(Url.urlDeauthenticate(), {headers: this.getAuthHeader(token), responseType: 'text'})
        .toPromise()
        .then(() => Promise.resolve());
    }

    public async projectnames(): Promise<string[]> {
        return this.http.get<string[]>(Url.urlProjectNames()).toPromise();
    }

    public createElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http
            .post(Url.urlCreate(element.url), payload, {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError).then((response: Response) => { });
    }

    public readElement(url: string, token: UserToken): Promise<IContainer> {
        return this.http
            .get<IContainer>(Url.urlElement(url), {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError)
            .then((element: IContainer) => element);
    }

    public readContents(url: string, token: UserToken): Promise<IContainer[]> {
        return this.http
            .get<IContainer[]>(Url.urlContents(url), {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError)
            .then((contents: IContainer[]) => contents);
    }

    public updateElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http
            .put(Url.urlUpdate(element.url), payload, {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError);
    }

    public deleteElement(url: string, token: UserToken): Promise<void> {
        return this.http
            .delete(Url.urlDelete(url), {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError);
    }

    public performOperation(url: string, serviceSuffix: string, payload: any, token: UserToken): Promise<void> {
        return this.http
            .post(Url.urlCustomService(url, serviceSuffix), payload, {headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError);
    }

    public performQuery(url: string, serviceSuffix: string, parameters: {[key: string]: string}, token: UserToken): Promise<any> {
        let urlParams = new HttpParams();
        for (let key in parameters) {
            if (parameters[key]) {
                urlParams = urlParams.append(key, parameters[key]);
            }
        }
        return this.http
            .get(Url.urlCustomService(url, serviceSuffix), {params: urlParams, headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError)
            .then((data: any) => data);
    }

    /** Perform a model search.
     * @param query     The query string
     * @param token     The current authentication token of the user
     * @param filter    Map from search fields (e.g. name) to queries.
     *                  If a search field begins with '-', this means results that match the query should be excluded.
     *                  Example: {'-name':'car'} --> Exclude results with 'car' in the name
     */
    public search(query: string, token: UserToken, filter?: {[key: string]: string}): Promise<IContainer[]> {
        let urlParams: HttpParams = new HttpParams();
        let queryString = query ? '+(' + query + ')' : '';
        if (filter) {
            for (let key in filter) {
                let modifier = '+';
                let field = key;
                if (key.startsWith('-')) {
                    modifier = '-';
                    field = key.substring(1);
                }
                queryString = queryString + ' ' + modifier + '(' + field + ':' + filter[key] + ')';
            }
        }
        urlParams = urlParams.append('query', queryString);
        return this.http
            .get<IContainer[]>(Url.urlCustomService(token.project, 'search'), {params: urlParams, headers: this.getAuthHeader(token)})
            .toPromise()
            .catch(this.handleError)
            .then((response: IContainer[]) => response);
    }

    private getParamsForQueryString(query: string): HttpParams {
        let phraseRegex: RegExp = new RegExp('\"[^\"]\"');
        let foundPhrase = '';
        let foundPhrases: string[] = [];
        do {
            let foundPhrase = phraseRegex.exec(query);
            if (foundPhrase) {
                foundPhrases.push(foundPhrase[0]);
            }
        } while (foundPhrase);
        query = query.replace(phraseRegex, '');
        let searchTerms: string[] = query.split(new RegExp('\s+'));
        searchTerms = searchTerms.concat(foundPhrases);

        let urlParams: HttpParams = new HttpParams();
        for (let i = 0; i < searchTerms.length; i++) {
            urlParams = urlParams.append('name', searchTerms[i]);
            urlParams = urlParams.append('description', searchTerms[i]);
            urlParams = urlParams.append('extid', searchTerms[i]);
        }
        return urlParams;
    }

    private handleError(error: any): Promise<any> {
        console.error('Error in Service Interface! (details below)');
        return Promise.reject(error);
    }

    private prepareElementPayload(element: IContainer): any {
        let payload: any = Objects.clone(element);
        payload.url = undefined;
        delete payload.url;
        if (Type.is(element, CEGConnection)) {
            payload.source.___proxy = 'true';
            payload.target.___proxy = 'true';
        }
        if (!element.id) {
            payload['___proxy'] = 'true';
        }
        return payload;
    }

    private getAuthHeader(token: UserToken): HttpHeaders {
        let headers: HttpHeaders = new HttpHeaders();
        if (token !== undefined && !UserToken.isInvalid(token)) {
            headers = headers.append('Authorization', 'Token ' + token.session.id);
        }
        return headers;
    }
}
