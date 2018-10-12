import { PositionableElementFactoryBase } from './positionable-element-factory-base';
import { ProcessDecision } from '../model/ProcessDecision';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { Config } from '../config/config';
import { ElementFactoryBase } from './element-factory-base';

export class ProcessDecisionFactory extends PositionableElementFactoryBase<ProcessDecision> {
    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<ProcessDecision> {

        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessDecision = new ProcessDecision();
        node.name = name || Config.PROCESS_NEW_DECISION_NAME + ' ' + ElementFactoryBase.getDateStr();
        node.description = Config.PROCESS_NEW_DECISION_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        node.tracesFrom = [];
        node.tracesTo = [];

        return this.dataService.createElement(node, true, compoundId).then(() => Promise.resolve(node));
    }

}
