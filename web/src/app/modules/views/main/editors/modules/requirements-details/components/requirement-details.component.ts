import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { Requirement } from '../../../../../../../model/Requirement';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SpecmateViewBase } from '../../../base/specmate-view-base';

@Component({
    moduleId: module.id.toString(),
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails extends SpecmateViewBase {

    requirement: Requirement;
    contents: IContainer[];
    relatedRequirements: IContainer[];

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService) {
        super(dataService, navigator, route, modal, translate);
    }

    protected onElementResolved(element: IContainer): void {
        this.requirement = element as Requirement;
        this.dataService.readContents(this.requirement.url).then((contents: IContainer[]) => this.contents = contents);
    }

    protected get isValid(): boolean {
        return true;
    }
}
