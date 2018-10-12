import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { Type } from '../../../../../../../util/type';
import { Process } from '../../../../../../../model/Process';
import { ProcessFactory } from '../../../../../../../factory/process-factory';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Id } from '../../../../../../../util/id';
import { Requirement } from '../../../../../../../model/Requirement';
import { Sort } from '../../../../../../../util/sort';

@Component({
    moduleId: module.id.toString(),
    selector: 'related-requirements-container',
    templateUrl: 'related-requirements-container.component.html',
    styleUrls: ['related-requirements-container.component.css']
})

export class RelatedRequirementsContainer extends ContentContainerBase<Requirement> {

    constructor(dataService: SpecmateDataService, navigator: NavigatorService, translate: TranslateService, modal: ConfirmationModal) {
        super(dataService, navigator, translate, modal);
    }

    private _parent: IContainer;

    protected condition = (element: IContainer) => true;

    protected get parent(): IContainer {
        return this._parent;
    }

    @Input()
    protected set parent(parent: IContainer) {
        this._parent = parent;
        this.readContents();
    }

    public createElement(name: string): Promise<Requirement> {
        throw new Error('Method not implemented.');
    }

    public async readContents(): Promise<void> {
        const contents = await this.dataService.performQuery(this.parent.url, 'related', { });
        this.contents = Sort.sortArray(contents);
    }

}
