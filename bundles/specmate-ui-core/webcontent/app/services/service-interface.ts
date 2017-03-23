import { Url } from '../util/Url';
import { IContainer } from '../model/IContainer';
import { Objects } from '../util/Objects';
import { Http, Response } from '@angular/http';
import { Type } from "../util/Type";
import { CEGConnection } from "../model/CEGConnection";

export class ServiceInterface {
    constructor(private http: Http) { }

    public createElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareCreateElementPayload(element);
        return this.http.post(Url.urlCreate(element.url), payload).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public readElement(url: string): Promise<IContainer> {
        return this.http.get(Url.urlElement(url)).toPromise().catch(this.handleError).then((response: Response) => response.json() as IContainer);
    }

    public readContents(url: string): Promise<IContainer[]> {
        return this.http.get(Url.urlContents(url)).toPromise().catch(this.handleError).then((response: Response) => response.json() as IContainer[]);
    }

    public updateElement(element: IContainer): Promise<void> {
        let payload: any = this.prepareCreateElementPayload(element);
        return this.http.put(Url.urlUpdate(element.url), payload).toPromise().catch(this.handleError).then((response: Response) => { });
    }

    public deleteElement(url: string): Promise<void> {
        return this.http.delete(Url.urlDelete(url)).toPromise().catch(this.handleError).catch(this.handleError).then((response: Response) => { });
    }

    private handleError(error: any): Promise<any> {
        console.log('Error in Service Interface! (details below)');
        return Promise.reject(error.message || error);
    }

    private prepareCreateElementPayload(element: IContainer): any {
        let payload: any = Objects.clone(element);
        payload.url = undefined;
        delete payload.url;
        if(Type.is(element, CEGConnection)) {
            payload.source.___proxy = true;
            payload.target.___proxy = true;
        }
        if(!element.id) {
            payload['___proxy'] = true;
        }
        return payload;
    }
}
