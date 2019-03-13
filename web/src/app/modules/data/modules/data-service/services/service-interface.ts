import { HttpClient, HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Url } from '../../../../../util/url';
import { IContainer } from '../../../../../model/IContainer';
import { Objects } from '../../../../../util/objects';
import 'rxjs/add/observable/of';
import { _throw } from 'rxjs/observable/throw';
import { UserToken } from '../../../../views/main/authentication/base/user-token';
import { UserSession } from '../../../../../model/UserSession';
import { User } from '../../../../../model/User';
import { BatchOperation } from '../../../../../model/BatchOperation';

export class ServiceInterface {

    constructor(private http: HttpClient) { }

    public async checkConnection(token?: UserToken): Promise<void> {
        if (token === undefined || token === UserToken.INVALID) {
            const error = new HttpErrorResponse({ status: 401 });
            return Promise.reject(error);
        }

        let params = new HttpParams();
        params = params.append('heartbeat', 'true');
        const result = await this.http.get(Url.urlCheckConnectivity(token.project), { headers: this.getAuthHeader(token), params: params })
            .toPromise();
        if (result instanceof HttpErrorResponse) {
            return Promise.reject(result);
        }
    }

    public async authenticate(user: User): Promise<UserToken> {
        const session: UserSession = await this.http.post(Url.urlAuthenticate(), user).toPromise() as UserSession;
        return new UserToken(session, user.projectName, session.libraryFolders);
    }

    public async deauthenticate(token: UserToken): Promise<void> {
        await  this.http.get(Url.urlDeauthenticate(), { headers: this.getAuthHeader(token), responseType: 'text' }).toPromise();
    }

    public async projectnames(): Promise<string[]> {
        return this.http.get<string[]>(Url.urlProjectNames()).toPromise();
    }

    public async performBatchOperation(batchOperation: BatchOperation, token: UserToken): Promise<void> {
        return this.http
            .post<void>(Url.batchOperationUrl(token), batchOperation, { headers: this.getAuthHeader(token) })
            .toPromise();
    }

    public async createElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        await this.http.post(Url.urlCreate(element.url), payload, { headers: this.getAuthHeader(token) }).toPromise();
    }

    public async readElement(url: string, token: UserToken): Promise<IContainer> {
        const element = await this.http.get<IContainer>(Url.urlElement(url), { headers: this.getAuthHeader(token) }).toPromise();
        return element;
    }

    public async readContents(url: string, token: UserToken): Promise<IContainer[]> {
        const contents = this.http.get<IContainer[]>(Url.urlContents(url), { headers: this.getAuthHeader(token) }).toPromise();
        return contents;
    }

    public async updateElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        await this.http.put(Url.urlUpdate(element.url), payload, { headers: this.getAuthHeader(token) }).toPromise();
    }

    public async deleteElement(url: string, token: UserToken): Promise<void> {
        await this.http.delete(Url.urlDelete(url), { headers: this.getAuthHeader(token) }).toPromise();
    }

    public async performOperation(url: string, serviceSuffix: string, payload: any, token: UserToken): Promise<any> {
        return await this.http.post(Url.urlCustomService(url, serviceSuffix), payload, { headers: this.getAuthHeader(token) }).toPromise();
    }

    public async performQuery(url: string, serviceSuffix: string, parameters: { [key: string]: string }, token: UserToken): Promise<any> {
        let urlParams = new HttpParams();
        for (let key in parameters) {
            if (parameters[key]) {
                urlParams = urlParams.append(key, parameters[key]);
            }
        }
        const result = await this.http
            .get(Url.urlCustomService(url, serviceSuffix), { params: urlParams, headers: this.getAuthHeader(token) }).toPromise();
        return result;
    }

    /** Perform a model search.
     * @param query     The query string
     * @param token     The current authentication token of the user
     * @param filter    Map from search fields (e.g. name) to queries.
     *                  If a search field begins with '-', this means results that match the query should be excluded.
     *                  Example: {'-name':'car'} --> Exclude results with 'car' in the name
     */
    public async search(query: string, token: UserToken, filter?: { [key: string]: string }): Promise<IContainer[]> {
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

        try {
            const response = await this.http
                .get<IContainer[]>(Url.urlCustomService(token.project, 'search'), { params: urlParams, headers: this.getAuthHeader(token) })
                .toPromise();
            return response;
        } catch (e) {
            this.handleError(e);
        }
    }

    private async handleError(error: any, url?: string): Promise<any> {
        console.error('Error in Service Interface! (details below) [' + url + ']');
        return Promise.reject(error);
    }

    private prepareElementPayload(element: IContainer): any {
        let payload: any = Objects.clone(element);
        payload.url = undefined;
        delete payload.url;
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
