import { Component } from '@angular/core';
import { SpecmateViewBase } from '../../../base/specmate-view-base';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { Process } from '../../../../../../../model/Process';
import { Requirement } from '../../../../../../../model/Requirement';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { Sort } from '../../../../../../../util/sort';
import { IContentElement } from '../../../../../../../model/IContentElement';
import { Id } from '../../../../../../../util/id';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { ProcessFactory } from '../../../../../../../factory/process-factory';
import { Url } from '../../../../../../../util/url';
import { TestSpecificationFactory } from '../../../../../../../factory/test-specification-factory';
import { Type } from '../../../../../../../util/type';
import { TestCase } from '../../../../../../../model/TestCase';
import { TranslateService } from '@ngx-translate/core';

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
