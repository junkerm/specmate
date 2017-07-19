import { ConfirmationModal } from './forms/confirmation-modal.service';
import { Location } from '@angular/common';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Component, Input, OnInit } from '@angular/core';
import { EditorCommonControlService } from '../../services/editor-common-control.service';

@Component({
    moduleId: module.id,
    selector: 'common-controls',
    templateUrl: 'common-controls.component.html',
    styleUrls: ['common-controls.component.css']
})
export class CommonControls {
    constructor(private dataService: SpecmateDataService, private commonControlService: EditorCommonControlService, private location: Location, private modal: ConfirmationModal) { }

    public save(): void {
        this.dataService.commit("Save");
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

    private back(): void {
        this.dataService.clearCommits();
        this.location.back();
    }

    public get isSaveEnabled(): boolean {
        return this.dataService.hasCommits && this.commonControlService.isCurrentEditorValid;
    }

    public get isEnabled(): boolean {
        return this.commonControlService.showCommonControls;
    }
}