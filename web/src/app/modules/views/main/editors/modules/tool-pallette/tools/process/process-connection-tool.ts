import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessConnectionFactory } from '../../../../../../../../factory/process-connection-factory';
import { IModelConnection } from '../../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../../model/IModelNode';
import { Process } from '../../../../../../../../model/Process';
import { ProcessConnection } from '../../../../../../../../model/ProcessConnection';
import { ConnectionToolBase } from '../connection-tool-base';

export class ProcessConnectionTool extends ConnectionToolBase<ProcessConnection> {

    protected modelType: { className: string; } = Process;

    public name = 'tools.addProcessConnection';
    public icon = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new ProcessConnectionFactory(e1, e2, this.dataService);
    }
}
