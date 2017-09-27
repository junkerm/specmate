import { SpecmateViewBase } from "../core/views/specmate-view-base";
import { Component } from "@angular/core";
import { IContainer } from "../../model/IContainer";
import { SpecmateDataService } from "../../services/data/specmate-data.service";
import { NavigatorService } from "../../services/navigation/navigator.service";
import { ActivatedRoute } from "@angular/router";
import { ConfirmationModal } from "../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from "../../services/common-controls/editor-common-control.service";

@Component({
    moduleId: module.id,
    selector: 'process-details',
    templateUrl: 'process-details.component.html',
    styleUrls: ['process-details.component.css']
})

export class ProcessDetails extends SpecmateViewBase {

    private process: IContainer;
    private contents: IContainer[];

    constructor(
        dataService: SpecmateDataService, 
        navigator: NavigatorService, 
        route: ActivatedRoute, 
        modal: ConfirmationModal, 
        editorCommonControlService: EditorCommonControlService) {
            super(dataService, navigator, route, modal, editorCommonControlService);
    }

    public onElementResolved(element: IContainer): void {
        this.process = element;
        this.dataService.readContents(this.process.url).then((contents: IContainer[]) => {
            this.contents = contents;
        });
    }
    
    public get isValid(): boolean {
        return true;
    }
}
