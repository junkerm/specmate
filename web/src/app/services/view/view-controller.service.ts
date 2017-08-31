import { Injectable } from '@angular/core';
import { Config } from "../../config/config";

@Injectable()
export class ViewControllerService {

    public get projectExplorerShown(): boolean {
        return true;
    }

    private _loggingOutputShown: boolean = Config.LOG_INITIALLY_SHOWN;
    public get loggingOutputShown(): boolean {
        return this._loggingOutputShown;
    }
    public set loggingOutputShown(loggingOutputShown: boolean) {
        this._loggingOutputShown = loggingOutputShown;
    }

    public showLoggingOutput(): void {
        this.loggingOutputShown = true;
    }

    public hideLoggingOutput(): void {
        this.loggingOutputShown = false;
    }

    constructor() { }
}