import { CEGConnectionFactory } from '../../../../../../../../factory/ceg-connection-factory';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { CEGModel } from '../../../../../../../../model/CEGModel';
import { IModelConnection } from '../../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../../model/IModelNode';
import { ConnectionToolBase } from '../connection-tool-base';

export class CEGConnectionTool extends ConnectionToolBase<CEGConnection> {

    protected modelType: { className: string; } = CEGModel;

    public name = 'tools.addCegConnection';
    public icon = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new CEGConnectionFactory(e1, e2, this.dataService);
    }
}
