import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Id } from '../../../../../../../util/id';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { TestSpecificationContentContainerBase } from '../base/testspecification-generatable-content-container-base';
import { AdditionalInformationService } from '../../../../../side/modules/links-actions/services/additional-information.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'ceg-model-container',
    templateUrl: 'ceg-model-container.component.html',
    styleUrls: ['ceg-model-container.component.css']
})

export class CEGModelContainer extends TestSpecificationContentContainerBase<CEGModel> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService,
        additionalInformationService: AdditionalInformationService) {
        super(dataService, navigator, translate, modal, clipboardService, additionalInformationService);
    }

    protected condition = (element: IContainer) => Type.is(element, CEGModel);

    public async createElement(name: string): Promise<CEGModel> {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        const element = await factory.create(this.parent, true, Id.uuid, name);
        return element as CEGModel;
    }
}
