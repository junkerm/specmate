import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { ProcessStep } from '../model/ProcessStep';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { PositionableElementFactoryBase } from './positionable-element-factory-base';

export class ProcessStepFactory extends PositionableElementFactoryBase<ProcessStep> {
    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<ProcessStep> {
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessStep = new ProcessStep();
        node.name = name || Config.PROCESS_NEW_STEP_NAME + ' ' + ElementFactoryBase.getDateStr();
        node.description = Config.PROCESS_NEW_STEP_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        node.tracesFrom = [];
        node.tracesTo = [];

        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }

}
