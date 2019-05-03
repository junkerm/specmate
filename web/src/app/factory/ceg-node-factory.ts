import { Config } from '../config/config';
import { CEGNode } from '../model/CEGNode';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { PositionableElementFactoryBase } from './positionable-element-factory-base';

export class CEGNodeFactory extends PositionableElementFactoryBase<CEGNode> {

    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<CEGNode> {

        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: CEGNode = new CEGNode();
        node.name = name || Config.CEG_NEW_NODE_NAME + ' ' + ElementFactoryBase.getDateStr();
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = Config.CEG_NODE_NEW_TYPE;
        node.variable = Config.CEG_NODE_NEW_VARIABLE;
        node.condition = Config.CEG_NODE_NEW_CONDITION;
        node.x = this.coords.x;
        node.y = this.coords.y;
        node.tracesFrom = [];
        node.tracesTo = [];

        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }
}
