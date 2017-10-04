import { SpecmateViewBase } from "../core/views/specmate-view-base";
import { Component, ChangeDetectorRef } from "@angular/core";
import { IContainer } from "../../model/IContainer";
import { SpecmateDataService } from "../../services/data/specmate-data.service";
import { NavigatorService } from "../../services/navigation/navigator.service";
import { ActivatedRoute } from "@angular/router";
import { ConfirmationModal } from "../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from "../../services/common-controls/editor-common-control.service";
import { TestSpecificationGenerator } from "../requirements/test-specification-generator";
import { Requirement } from "../../model/Requirement";
import { Url } from "../../util/Url";
import { Process } from "../../model/Process";

@Component({
    moduleId: module.id,
    selector: 'process-details',
    templateUrl: 'process-details.component.html',
    styleUrls: ['process-details.component.css']
})

export class ProcessDetails extends TestSpecificationGenerator {

    private process: IContainer;
    private contents: IContainer[];
    private model: Process;
    

    constructor(
        dataService: SpecmateDataService, 
        navigator: NavigatorService, 
        route: ActivatedRoute, 
        modal: ConfirmationModal, 
        editorCommonControlService: EditorCommonControlService,
        private changeDetectorRef: ChangeDetectorRef) {
            super(dataService, modal, route, navigator, editorCommonControlService);
    }

    ngDoCheck() {
        super.ngDoCheck();
        this.changeDetectorRef.detectChanges();
        if(this.model && this.contents) {
            this.doCheckCanCreateTestSpec(this.model, this.contents);
        }
    }

    public onElementResolved(element: IContainer): void {
        super.onElementResolved(element);
        this.process = element;
        this.model = element;
        this.dataService.readContents(this.model.url).then((contents: IContainer[]) => this.contents = contents);
    }

    protected resolveRequirement(element: IContainer): Promise<Requirement> {
        return this.dataService.readElement(Url.parent(element.url)).then((element: IContainer) => element as Requirement);
    }


    public get isValid(): boolean {
        return true;
    }
}
