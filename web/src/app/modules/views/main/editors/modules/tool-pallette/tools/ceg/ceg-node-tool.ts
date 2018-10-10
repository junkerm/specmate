import { CreateNodeToolBase } from '../create-node-tool-base';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { CEGModel } from '../../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../../model/IContainer';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { CEGNodeFactory } from '../../../../../../../../factory/ceg-node-factory';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';

export class CEGNodeTool extends CreateNodeToolBase<CEGNode> {

    protected modelType: { className: string; } = CEGModel;

    public icon = 'plus';
    public name = 'tools.addCegNode';

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<CEGNode> {
        return new CEGNodeFactory(coords, this.dataService);
    }
}
