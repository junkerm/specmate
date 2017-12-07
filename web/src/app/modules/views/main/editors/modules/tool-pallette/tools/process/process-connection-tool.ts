import { ConnectionToolBase } from '../connection-tool-base';
import { Process } from '../../../../../../../../model/Process';
import { ProcessConnection } from '../../../../../../../../model/ProcessConnection';
import { IModelNode } from '../../../../../../../../model/IModelNode';
import { ElementFactoryBase } from '../../../../../../../../factory/element-factory-base';
import { ProcessConnectionFactory } from '../../../../../../../../factory/process-connection-factory';
import { IModelConnection } from '../../../../../../../../model/IModelConnection';

export class ProcessConnectionTool extends ConnectionToolBase<ProcessConnection> {

    protected modelType: { className: string; } = Process;
    
    public name: string = 'Add Connection';
    public icon: string = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new ProcessConnectionFactory(e1, e2, this.dataService);
    }
}
