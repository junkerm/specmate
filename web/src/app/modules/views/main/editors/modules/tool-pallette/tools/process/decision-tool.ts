import { CreateNodeToolBase } from '../create-node-tool-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { Process } from '../../../../../../../../model/Process';
import { ProcessDecision } from '../../../../../../../../model/ProcessDecision';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessDecisionFactory } from '../../../../../../../../factory/process-decision-factory';


export class DecisionTool extends CreateNodeToolBase<ProcessDecision> {

    protected modelType: { className: string; } = Process;
    
    public name: string = "Add Decision";
    public icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessDecision> {
        return new ProcessDecisionFactory(coords, this.dataService);
    }
}