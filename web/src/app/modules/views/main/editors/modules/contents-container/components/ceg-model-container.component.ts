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
import { SelectTool } from '../../tool-pallette/tools/common/select-tool';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { Objects } from '../../../../../../../util/objects';
import { GraphTransformer } from '../../tool-pallette/util/graphTransformer';

@Component({
    moduleId: module.id.toString(),
    selector: 'ceg-model-container',
    templateUrl: 'ceg-model-container.component.html',
    styleUrls: ['ceg-model-container.component.css']
})

export class CEGModelContainer extends ContentContainerBase<CEGModel> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        private clipboardService: ClipboardService) {
        super(dataService, navigator, translate, modal);
    }

    protected condition = (element: IContainer) => Type.is(element, CEGModel);

    public async createElement(name: string): Promise<CEGModel> {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        const element = await factory.create(this.parent, true, Id.uuid, name);
        return element as CEGModel;
    }

    public async copy(model: CEGModel): Promise<void> {
        const copy = Objects.clone(model) as CEGModel;
        copy.id = Id.uuid;
        // delete copy.url;
        copy.name = 'Copy of ' + model.name;
        copy.tracesFrom = [];
        copy.tracesTo = [];
        this.clipboardService.clipboard = [copy];
    }

    public async paste(): Promise<void> {
        if (!this.canPaste) {
            return;
        }
        const model = this.clipboardService.clipboard[0];
        const copy = await this.createElement(model.name);
        const contents = await this.dataService.readContents(model.url, true);
        const transformer = new GraphTransformer(this.dataService, undefined, copy);
        await transformer.cloneSubgraph(contents, Id.uuid, true);
        await this.dataService.commit(this.translate.instant('paste'));
        await this.readContents();
    }

    public get canPaste(): boolean {
        return this.clipboardService.clipboard.length === 1 && Type.is(this.clipboardService.clipboard[0], CEGModel);
    }
}
