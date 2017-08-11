import { Config } from '../../config/config';
import { ConfirmationModal } from './forms/confirmation-modal.service';
import { Location } from '@angular/common';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Component, Input, OnInit } from '@angular/core';
import { EditorCommonControlService } from '../../services/editor-common-control.service';
import { Observable } from 'rxjs/Rx';
import { NavigatorService } from "../../services/navigator.service";

@Component({
    moduleId: module.id,
    selector: 'common-controls',
    templateUrl: 'common-controls.component.html',
    styleUrls: ['common-controls.component.css']
})
export class CommonControls {
    
    public connected : boolean = true;
    
    constructor(private dataService: SpecmateDataService, private commonControlService: EditorCommonControlService, private modal: ConfirmationModal, private navigator: NavigatorService) {
        let timer = Observable.timer(0, Config.CONNECTIVITY_CHECK_DELAY);
        timer.subscribe(() => {
            this.dataService.checkConnection().then((val: boolean) => this.connected = val);
        });
    }

    public save(): void {
        if(this.isSaveEnabled) {
            this.dataService.commit("Save");
        }
    }

    public close(): void {
        this.back();
    }

    public undo(): void {
        if(this.isUndoEnabled) {
            this.dataService.undo();
        }
    }

    private forward(): void {
        if(this.isForwardEnabled) {
            this.navigator.forward();
        }
    }

    private back(): void {
        if(this.isBackEnabled) {
            this.navigator.back();
        }
    }

    public get isSaveEnabled(): boolean {
        return this.isEnabled && this.dataService.hasCommits && this.commonControlService.isCurrentEditorValid;
    }

    public get isUndoEnabled(): boolean {
        return this.isEnabled && this.dataService.hasCommits;
    }

    public get isBackEnabled(): boolean {
        return this.navigator.hasPrevious;
    }

    public get isForwardEnabled(): boolean {
        return this.navigator.hasNext;
    }

    public get isEnabled(): boolean {
        return true;
    }
}