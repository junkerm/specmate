import { Injectable } from '@angular/core';

@Injectable()
export class EditorCommonControlService {

    public isCurrentEditorValid: boolean = false;
    public showCommonControls: boolean = false;

    constructor() { }
}
