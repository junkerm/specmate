import { Injectable } from '@angular/core';
import { IContainer } from '../../../../../../../model/IContainer';

@Injectable()
export class ClipboardService {

    constructor() {}

    private static _clipboard: IContainer[] = [];
    public get clipboard(): IContainer[] {
        return ClipboardService._clipboard;
    }

    public set clipboard(newClip: IContainer[]) {
        if (!newClip) {
            newClip = [];
        }
        ClipboardService._clipboard = newClip;
    }

    public hasClipboard(): boolean {
        return ClipboardService._clipboard.length > 0;
    }

    public clear(): void {
        ClipboardService._clipboard = [];
    }
}
