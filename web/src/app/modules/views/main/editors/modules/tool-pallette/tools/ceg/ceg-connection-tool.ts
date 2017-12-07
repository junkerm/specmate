import { ConnectionToolBase } from '../../tools/connection-tool-base';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { CEGModel } from '../../../../../../../../model/CEGModel';
import { IModelNode } from '../../../../../../../../model/IModelNode';
import { IModelConnection } from '../../../../../../../../model/IModelConnection';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { CEGConnectionFactory } from '../../../../../../../../factory/ceg-connection-factory';

export class CEGConnectionTool extends ConnectionToolBase<CEGConnection> {

    protected modelType: { className: string; } = CEGModel;

    public name = 'Add Connection';
    public icon = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new CEGConnectionFactory(e1, e2, this.dataService);
    }
}
