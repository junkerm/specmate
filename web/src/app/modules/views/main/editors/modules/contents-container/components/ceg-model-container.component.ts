import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { Id } from '../../../../../../../util/id';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { AdditionalInformationService } from '../../../../../side/modules/links-actions/services/additional-information.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { TestSpecificationContentContainerBase } from '../base/testspecification-generatable-content-container-base';

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

    modelDescription: string;


    protected condition = (element: IContainer) => Type.is(element, CEGModel);

    public async createElement(name: string): Promise<CEGModel> {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        const element = await factory.create(this.parent, true, Id.uuid, name) as CEGModel;

        if (this.modelDescription == undefined) {
            this.modelDescription = '';
        }
        this.modelDescription = this.modelDescription.trim();

        if (this.modelDescription.length > 0) {
            element.modelRequirements = this.modelDescription;
            await this.dataService.updateElement(element, true, Id.uuid);
            await this.dataService.commit(this.translate.instant('save'));
            await this.dataService.performOperations(element.url, 'generateModel');
            await this.dataService.deleteCachedContent(element.url);
            await this.dataService.readElement(element.url, false);
            const content = await this.dataService.readContents(element.url, false);
            if (content.length == 0) {
                // Nothing was generated --> Delete the empty model
                await this.dataService.deleteElement(element.url, true, Id.uuid);
                await this.dataService.commit(this.translate.instant('save'));
                await this.dataService.deleteCachedContent(element.url);
                await this.modal.openOk(this.translate.instant('CEGGenerator.couldNotGenerateTitle'), 
                        this.translate.instant('CEGGenerator.couldNotGenerate'));
                return undefined;
            }
        }
        return element;
    }
}
