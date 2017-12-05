import { CEGConnection } from "../../../../../model/CEGConnection";
import { ConnectionToolBase } from "../connection-tool-base";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { IModelNode } from "../../../../../model/IModelNode";
import { Type } from "../../../../../util/Type";
import { CEGNode } from "../../../../../model/CEGNode";
import { CEGModel } from "../../../../../model/CEGModel";
import { CEGConnectionFactory } from "../../../../../factory/ceg-connection-factory";
import { ElementFactoryBase } from "../../../../../factory/element-factory-base";
import { IModelConnection } from "../../../../../model/IModelConnection";

export class CEGConnectionTool extends ConnectionToolBase<CEGConnection> {

    protected modelType: { className: string; } = CEGModel;

    public name: string = 'Add Connection';
    public icon: string = 'sitemap';

    protected getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection> {
        return new CEGConnectionFactory(e1, e2, this.dataService);
    }
}
