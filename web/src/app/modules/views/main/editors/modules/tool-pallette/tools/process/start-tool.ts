import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessStartFactory } from '../../../../../../../../factory/process-start-factory';
import { IContainer } from '../../../../../../../../model/IContainer';
import { Process } from '../../../../../../../../model/Process';
import { ProcessStart } from '../../../../../../../../model/ProcessStart';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { CreateNodeToolBase } from '../create-node-tool-base';

export class StartTool extends CreateNodeToolBase<ProcessStart> {

    protected modelType: { className: string; } = Process;

    public name = 'tools.addStart';
    public icon = 'plus';
    public sticky = false;
    public style = 'shape=ellipse';

    constructor(parent: IContainer,
        dataService: SpecmateDataService,
        selectedElementService: SelectedElementService) {
        super(dataService, selectedElementService, parent);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessStart> {
        return new ProcessStartFactory(coords, this.dataService);
    }
}
