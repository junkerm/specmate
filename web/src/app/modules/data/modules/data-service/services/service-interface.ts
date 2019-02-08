import { HttpClient, HttpResponse, HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Url } from '../../../../../util/url';
import { IContainer } from '../../../../../model/IContainer';
import { Objects } from '../../../../../util/objects';
import { CEGConnection } from '../../../../../model/CEGConnection';
import { Type } from '../../../../../util/type';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { retryWhen, mergeMap, finalize, catchError } from 'rxjs/operators';
import { _throw } from 'rxjs/observable/throw';
import { UserToken } from '../../../../views/main/authentication/base/user-token';
import { UserSession } from '../../../../../model/UserSession';
import { User } from '../../../../../model/User';
import { BatchOperation } from '../../../../../model/BatchOperation';
import { Config } from '../../../../../config/config';
import { timer } from 'rxjs/observable/timer';
import { PromiseObservable } from 'rxjs/observable/PromiseObservable';

export class ServiceInterface {

    constructor(private http: HttpClient) { }

    public checkConnection(token?: UserToken): Promise<void> {
        if (token === undefined || token === UserToken.INVALID) {
            const error = new HttpErrorResponse({ status: 401 });
            return Promise.reject(error);
        }

        const retryOptions = new RetryStrategyOptions();

        retryOptions.maxRetryAttempts = 2;
        retryOptions.includedStatusCodes = [404];
        retryOptions.delay = 100;

        let params = new HttpParams();
        params = params.append('heartbeat', 'true');
        return this.toRetryPromise(this.http
            .get(Url.urlCheckConnectivity(token.project), { headers: this.getAuthHeader(token), params: params }), retryOptions)
            .then(result => {
                if (result instanceof HttpErrorResponse) {
                    return Promise.reject(result);
                }
                return Promise.resolve();
            });
    }

    public async authenticate(user: User): Promise<UserToken> {
        const session: UserSession = await this.http.post(Url.urlAuthenticate(), user).toPromise() as UserSession;
        return new UserToken(session, user.projectName, session.libraryFolders);
    }

    public deauthenticate(token: UserToken): Promise<void> {
        return this.toRetryPromise(this.http
            .get(Url.urlDeauthenticate(), { headers: this.getAuthHeader(token), responseType: 'text' }))
            .then(() => Promise.resolve());
    }

    public async projectnames(): Promise<string[]> {
        return this.toRetryPromise(this.http.get<string[]>(Url.urlProjectNames()));
    }

    public async performBatchOperation(batchOperation: BatchOperation, token: UserToken): Promise<void> {
        return this.toRetryPromise(this.http
            .post<void>(Url.batchOperationUrl(token), batchOperation, { headers: this.getAuthHeader(token) }));
    }

    public createElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.toRetryPromise(this.http
            .post(Url.urlCreate(element.url), payload, { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), element.url)
            .then(() => { });
    }

    public readElement(url: string, token: UserToken): Promise<IContainer> {
        return this.toRetryPromise(this.http
            .get<IContainer>(Url.urlElement(url), { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), url)
            .then((element: IContainer) => element);
    }

    public readContents(url: string, token: UserToken): Promise<IContainer[]> {
        return this.toRetryPromise(this.http
            .get<IContainer[]>(Url.urlContents(url), { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), url)
            .then((contents: IContainer[]) => contents);
    }

    public updateElement(element: IContainer, token: UserToken): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.toRetryPromise(this.http
            .put(Url.urlUpdate(element.url), payload, { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), element.url);
    }

    public deleteElement(url: string, token: UserToken): Promise<void> {
        return this.toRetryPromise(this.http
            .delete(Url.urlDelete(url), { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), url);
    }

    public performOperation(url: string, serviceSuffix: string, payload: any, token: UserToken): Promise<void> {
        return this.toRetryPromise(this.http
            .post(Url.urlCustomService(url, serviceSuffix), payload,
                { headers: this.getAuthHeader(token) }), new RetryStrategyOptions(),
            url);
    }

    public performQuery(url: string, serviceSuffix: string, parameters: { [key: string]: string }, token: UserToken): Promise<any> {
        let urlParams = new HttpParams();
        for (let key in parameters) {
            if (parameters[key]) {
                urlParams = urlParams.append(key, parameters[key]);
            }
        }
        return this.toRetryPromise(this.http
            .get(Url.urlCustomService(url, serviceSuffix),
                { params: urlParams, headers: this.getAuthHeader(token) }), new RetryStrategyOptions(), url)
            .then((data: any) => data);
    }

    /** Perform a model search.
     * @param query     The query string
     * @param token     The current authentication token of the user
     * @param filter    Map from search fields (e.g. name) to queries.
     *                  If a search field begins with '-', this means results that match the query should be excluded.
     *                  Example: {'-name':'car'} --> Exclude results with 'car' in the name
     */
    public search(query: string, token: UserToken, filter?: { [key: string]: string }): Promise<IContainer[]> {
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
            .get<IContainer[]>(Url.urlCustomService(token.project, 'search'), { params: urlParams, headers: this.getAuthHeader(token) })
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

    private handleError(error: any, url?: string): Promise<any> {
        console.error('Error in Service Interface! (details below) [' + url + ']');
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

    private toRetryPromise(req: Observable<any>, options: RetryStrategyOptions = new RetryStrategyOptions(), url?: string): Promise<any> {
        return req.pipe(
            retryWhen(genericRetryStrategy(options)),
            catchError(error => Observable.of(error)))
            .toPromise().catch(e => this.handleError(e, url));
    }
}

class RetryStrategyOptions {
    public maxRetryAttempts: number = Config.NUM_HTTP_RETRIES;
    public delay: number = Config.HTTP_RETRY_DELAY;
    public includedStatusCodes: number[] = Config.HTTP_RETRY_ERRORS;
}

export const genericRetryStrategy = (options: RetryStrategyOptions) => (attempts: Observable<any>) => {
    return attempts.pipe(
        mergeMap((error, i) => {
            const retryAttempt = i + 1;
            if (retryAttempt > options.maxRetryAttempts || options.includedStatusCodes.indexOf(error.status) < 0) {
                return _throw(error);
            }
            console.log(`Attempt ${retryAttempt}: retrying in ${options.delay}ms`);
            return timer(options.delay);
        }),
        finalize(() => { })
    );
};
