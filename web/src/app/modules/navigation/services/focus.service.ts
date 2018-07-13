import { Injectable } from '@angular/core';

@Injectable()
export class FocusService {
    private _focusList: any[];

    constructor() {
        this._focusList = [];
    }

    demandFocus(component: any): void {
        this.returnFocus(component);
        this._focusList.push(component);
    }

    returnFocus(component: any): void {
        // Remove component from focus
        let ind = this._focusList.indexOf(component);
        if (ind >= 0) {
            this._focusList.splice(ind, 1);
        }
    }

    public isFocused(component: any): boolean {
        if (this._focusList.length > 0) {
            return this._focusList[this._focusList.length - 1] === component;
        }
        return false;
    }
}
