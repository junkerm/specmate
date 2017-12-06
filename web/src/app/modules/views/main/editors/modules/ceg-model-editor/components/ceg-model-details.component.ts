import { Component, ViewChild } from "@angular/core";
import { SpecmateViewBase } from "../../../base/specmate-view-base";
import { CEGModel } from "../../../../../../../model/CEGModel";
import { IContainer } from "../../../../../../../model/IContainer";
import { GraphicalEditor } from "../../graphical-editor/components/graphical-editor.component";
import { SpecmateDataService } from "../../../../../../data/modules/data-service/services/specmate-data.service";
import { NavigatorService } from "../../../../../../navigation/modules/navigator/services/navigator.service";
import { ActivatedRoute } from "@angular/router";
import { ConfirmationModal } from "../../../../../../notification/modules/modals/services/confirmation-modal.service";
import { EditorCommonControlService } from "../../../../../../actions/modules/common-controls/services/common-control.service";

@Component({
    moduleId: module.id,
    selector: 'ceg-model-details-editor',
    templateUrl: 'ceg-model-details.component.html',
    styleUrls: ['ceg-model-details.component.css']
})

export class CEGModelDetails extends SpecmateViewBase {

    private model: CEGModel;
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

    protected get isValid(): boolean {
        if (!this.editor) {
            return true;
        }
        return this.editor.isValid;
    }
}
