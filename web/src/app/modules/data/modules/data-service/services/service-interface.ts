import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Url } from '../../../../../util/url';
import { IContainer } from '../../../../../model/IContainer';
import { Objects } from '../../../../../util/objects';
import { CEGConnection } from '../../../../../model/CEGConnection';
import { Type } from '../../../../../util/type';
import 'rxjs/add/operator/toPromise';

export class ServiceInterface {
    constructor(private http: HttpClient) { }

    public checkConnection(): Promise<boolean> {
        return this.http.get(Url.urlCheckConnectivity()).toPromise().then(() => true).catch(() => false);
    }

    public createElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http.post(Url.urlCreate(element.url), payload).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public readElement(url: string): Promise<IContainer> {
        return this.http
            .get<IContainer>(Url.urlElement(url)).toPromise()
            .catch(this.handleError)
            .then((element: IContainer) => element);
    }

    public readContents(url: string): Promise<IContainer[]> {
        return this.http
            .get<IContainer[]>(Url.urlContents(url)).toPromise()
            .catch(this.handleError)
            .then((contents: IContainer[]) => contents);
    }

    public updateElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http.put(Url.urlUpdate(element.url), payload).toPromise().catch(this.handleError);
    }

    public deleteElement(url: string): Promise<void> {
        return this.http.delete(Url.urlDelete(url)).toPromise().catch(this.handleError);
    }

    public performOperation(url: string, serviceSuffix: string, payload: any): Promise<void> {
        return this.http.post(Url.urlCustomService(url, serviceSuffix), payload).toPromise().catch(this.handleError);
    }

    public performQuery(url: string, serviceSuffix: string, parameters: {[key: string]: string} ): Promise<any> {
        let urlParams = new HttpParams();
        for (let key in parameters) {
            if (parameters[key]) {
                urlParams = urlParams.append(key, parameters[key]);
            }
        }
        return this.http
            .get(Url.urlCustomService(url, serviceSuffix), {params: urlParams}).toPromise()
            .catch(this.handleError)
            .then((data: any) => data);
    }

    public search(query: string, filter?: {[key: string]: string}): Promise<IContainer[]> {
        let urlParams: HttpParams = new HttpParams();
        let queryString = query ? '+(' + query + ')' : '';
        if (filter) {
            for (let key in filter) {
                queryString = queryString + ' +(' + key + ':' + filter[key] + ')';
            }
        }
        urlParams = urlParams.append('query', queryString);
        return this.http
            .get<IContainer[]>(Url.urlCustomService('', 'search'), {params: urlParams}).toPromise()
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
        return Promise.reject(error.message || error);
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
}
