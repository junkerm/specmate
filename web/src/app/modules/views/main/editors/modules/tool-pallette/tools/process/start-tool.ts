import { CreateNodeToolBase } from '../create-node-tool-base';
import { Process } from '../../../../../../../../model/Process';
import { ProcessStart } from '../../../../../../../../model/ProcessStart';
import { IContainer } from '../../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessStartFactory } from '../../../../../../../../factory/process-start-factory';

export class StartTool extends CreateNodeToolBase<ProcessStart> {

    protected modelType: { className: string; } = Process;

    public name = 'tools.addStart';
    public icon = 'plus';

    constructor(parent: IContainer,
        dataService: SpecmateDataService,
        selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessStart> {
        return new ProcessStartFactory(coords, this.dataService);
    }
}
