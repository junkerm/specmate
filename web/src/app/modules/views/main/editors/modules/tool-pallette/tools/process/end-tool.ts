import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessEndFactory } from '../../../../../../../../factory/process-end-factory';
import { IContainer } from '../../../../../../../../model/IContainer';
import { Process } from '../../../../../../../../model/Process';
import { ProcessEnd } from '../../../../../../../../model/ProcessEnd';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { CreateNodeToolBase } from '../create-node-tool-base';

export class EndTool extends CreateNodeToolBase<ProcessEnd> {

    protected modelType: { className: string; } = Process;

    public name = 'tools.addEnd';
    public icon = 'plus';
    public sticky = false;

    constructor(parent: IContainer,
        dataService: SpecmateDataService,
        selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessEnd> {
        return new ProcessEndFactory(coords, this.dataService);
    }
}
