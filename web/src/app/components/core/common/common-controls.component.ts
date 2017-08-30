import { Config } from '../../../config/config';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { Location } from '@angular/common';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { Component, Input, OnInit } from '@angular/core';
import { EditorCommonControlService } from '../../../services/common-controls/editor-common-control.service';
import { NavigatorService } from "../../../services/navigation/navigator.service";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';

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

    public get themes(): string[] {
        return ['cosmo', 'slate', 'yeti'].sort();
    }

    public set currentTheme(name: string) {
        let href: string = 'https://bootswatch.com/4-alpha/' + name + '/bootstrap.min.css';
        document.getElementById('bootstrap-link').setAttribute('href', href);
    }

    public get currentTheme(): string {
        return this.themeLinkElement.getAttribute('href').replace('https://bootswatch.com/4-alpha/', '').replace('/bootstrap.min.css','');
    }

    private get themeLinkElement(): HTMLElement {
        return document.getElementById('bootstrap-link');
    }
}