import { CEGNodeFactory } from '../../../../../../../../factory/ceg-node-factory';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { CEGModel } from '../../../../../../../../model/CEGModel';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { CreateNodeToolBase } from '../create-node-tool-base';

export class CEGNodeTool extends CreateNodeToolBase<CEGNode> {

    protected modelType: { className: string; } = CEGModel;

    public icon = 'plus';
    public name = 'tools.addCegNode';

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<CEGNode> {
        return new CEGNodeFactory(coords, this.dataService);
    }
}
