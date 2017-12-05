import { ConnectionToolBase } from "../connection-tool-base";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { IModelNode } from "../../../../../model/IModelNode";
import { Type } from "../../../../../util/Type";
import { ProcessConnection } from "../../../../../model/ProcessConnection";
import { ProcessStart } from "../../../../../model/ProcessStart";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { ProcessEnd } from "../../../../../model/ProcessEnd";
import { Process } from "../../../../../model/Process";
import { ElementFactoryBase } from "../../../../../factory/element-factory-base";
import { IModelConnection } from "../../../../../model/IModelConnection";
import { ProcessConnectionFactory } from "../../../../../factory/process-connection-factory";

export class ProcessConnectionTool extends ConnectionToolBase<ProcessConnection> {

    protected modelType: { className: string; } = Process;
    
    public name: string = 'Add Connection';
    public icon: string = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new ProcessConnectionFactory(e1, e2, this.dataService);
    }
}
