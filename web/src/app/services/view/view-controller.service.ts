import { Injectable } from '@angular/core';

@Injectable()
export class ViewControllerService {

    public get projectExplorerShown(): boolean {
        return true;
    }

    private _loggingOutputShown: boolean = true;
    public get loggingOutputShown(): boolean {
        return this._loggingOutputShown;
    }
    public set loggingOutputShown(loggingOutputShown: boolean) {
        this._loggingOutputShown = loggingOutputShown;
    }

    public showLoggingOutput(): void {
        this.loggingOutputShown = true;
    }

    constructor() { }
}