import {Type} from '../../util/Type';
import {TestSpecification} from '../../model/TestSpecification';
import { SpecmateViewBase } from "../core/views/specmate-view-base";
import { Component, ChangeDetectorRef, ViewChild } from "@angular/core";
import { IContainer } from "../../model/IContainer";
import { SpecmateDataService } from "../../services/data/specmate-data.service";
import { NavigatorService } from "../../services/navigation/navigator.service";
import { ActivatedRoute } from "@angular/router";
import { ConfirmationModal } from "../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from "../../services/common-controls/editor-common-control.service";
import { Requirement } from "../../model/Requirement";
import { Url } from "../../util/Url";
import { Process } from "../../model/Process";
import { GraphicalEditor } from '../core/graphical/graphical-editor.component';

@Component({
    moduleId: module.id,
    selector: 'process-details',
    templateUrl: 'process-details.component.html',
    styleUrls: ['process-details.component.css']
})

export class ProcessDetails extends SpecmateViewBase {

    private process: IContainer;
    private contents: IContainer[];
    private model: Process;
    
    @ViewChild(GraphicalEditor)
    private processEditor: GraphicalEditor;

    constructor(
        dataService: SpecmateDataService, 
        navigator: NavigatorService, 
        route: ActivatedRoute, 
        modal: ConfirmationModal, 
        editorCommonControlService: EditorCommonControlService) {
            super(dataService, navigator, route, modal, editorCommonControlService);
    }
        
    protected onElementResolved(element: IContainer): void {
        this.model = element;
    }

    public get isValid(): boolean {
        if(!this.processEditor) {
            return true;
        }
        return this.processEditor.isValid;
    }
}
