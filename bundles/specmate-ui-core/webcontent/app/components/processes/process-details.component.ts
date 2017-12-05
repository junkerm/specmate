import { Component, ViewChild } from "@angular/core";
import { SpecmateViewBase } from "../core/views/specmate-view-base";
import { GraphicalEditor } from "../core/graphical/graphical-editor.component";
import { Process } from "../../model/Process";
import { IContainer } from "../../model/IContainer";
import { SpecmateDataService } from "../../services/data/specmate-data.service";
import { NavigatorService } from "../../services/navigation/navigator.service";
import { ActivatedRoute } from "@angular/router";
import { ConfirmationModal } from "../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from "../../services/common-controls/editor-common-control.service";
import { Requirement } from "../../model/Requirement";
import { Url } from "../../util/Url";

@Component({
    moduleId: module.id,
    selector: 'process-details',
    templateUrl: 'process-details.component.html',
    styleUrls: ['process-details.component.css']
})

export class ProcessDetails extends SpecmateViewBase {

    private model: Process;
    private contents: IContainer[];
    
    @ViewChild(GraphicalEditor)
    private editor: GraphicalEditor;

    constructor(dataService: SpecmateDataService, navigator: NavigatorService, route: ActivatedRoute, modal: ConfirmationModal, editorCommonControlService: EditorCommonControlService) {
        super(dataService, navigator, route, modal, editorCommonControlService);
    }

    protected onElementResolved(element: IContainer): void {
        this.model = element;
        this.dataService.readContents(this.model.url).then((contents: IContainer[]) => this.contents = contents);
    }

    public get isValid(): boolean {
        if(!this.editor) {
            return true;
        }
        return this.editor.isValid;
    }
}
