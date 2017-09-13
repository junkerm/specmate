import { Url } from '../../util/Url';
import { IContainer } from '../../model/IContainer';
import { Objects } from '../../util/Objects';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Type } from "../../util/Type";
import { CEGConnection } from "../../model/CEGConnection";
import 'rxjs/add/operator/toPromise';

export class ServiceInterface {
    constructor(private http: Http) { }

    public checkConnection() : Promise<boolean> {
        return this.http.get(Url.urlCheckConnectivity()).toPromise().then(() => true).catch(() => false);
    }

    public createElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http.post(Url.urlCreate(element.url), payload).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public readElement(url: string): Promise<IContainer> {
        return this.http.get(Url.urlElement(url)).toPromise().catch(this.handleError).then((response: Response) => response.json() as IContainer);
    }

    public readContents(url: string): Promise<IContainer[]> {
        return this.http.get(Url.urlContents(url)).toPromise().catch(this.handleError).then((response: Response) => response.json() as IContainer[]);
    }

    public updateElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareElementPayload(element);
        return this.http.put(Url.urlUpdate(element.url), payload).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public deleteElement(url: string): Promise<void> {
        return this.http.delete(Url.urlDelete(url)).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public performOperation(url: string, serviceSuffix: string, payload: any): Promise<void> {
        return this.http.post(Url.urlCustomService(url, serviceSuffix), payload)
        .toPromise().catch(this.handleError)
        .then((response: Response) => {
           return response.json();
        });
    }

    public performQuery(url: string, serviceSuffix: string, parameters:  { [key:string]:string; } ): Promise<any> {
        let urlParams: URLSearchParams = new URLSearchParams();
        for(let key in parameters){
            urlParams.append(key, parameters[key]);
        }
        return this.http.get(Url.urlCustomService(url, serviceSuffix),{search: urlParams}).toPromise().catch(this.handleError).then((response: Response) => response.json());
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
