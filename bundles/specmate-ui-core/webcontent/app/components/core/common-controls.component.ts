import { Config } from '../../config/config';
import { ConfirmationModal } from './forms/confirmation-modal.service';
import { Location } from '@angular/common';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Component, Input, OnInit } from '@angular/core';
import { EditorCommonControlService } from '../../services/editor-common-control.service';
import { Observable } from 'rxjs/Rx';

@Component({
    moduleId: module.id,
    selector: 'common-controls',
    templateUrl: 'common-controls.component.html',
    styleUrls: ['common-controls.component.css']
})
export class CommonControls {
    
    public connected : boolean = true;
    
    constructor(private dataService: SpecmateDataService, private commonControlService: EditorCommonControlService, private location: Location, private modal: ConfirmationModal) {
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
        if(this.dataService.hasCommits) {
             this.modal.open('You have unsaved changes. Do you want to discard them?')
            .then(() => this.back())
            .catch(() => { });
        }
        else {
            this.back();
        }
    }

    public undo(): void {
        if(this.isUndoEnabled) {
            this.dataService.undo();
        }
    }

    private back(): void {
        this.dataService.clearCommits();
        this.location.back();
    }

    public get isSaveEnabled(): boolean {
        return this.isEnabled && this.dataService.hasCommits && this.commonControlService.isCurrentEditorValid;
    }

    public get isUndoEnabled(): boolean {
        return this.isEnabled && this.dataService.hasCommits;
    }

    public get isEnabled(): boolean {
        return this.commonControlService.showCommonControls;
    }
}