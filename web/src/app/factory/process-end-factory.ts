import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { ProcessEnd } from '../model/ProcessEnd';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { PositionableElementFactoryBase } from './positionable-element-factory-base';

export class ProcessEndFactory extends PositionableElementFactoryBase<ProcessEnd> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<ProcessEnd> {
        compoundId = compoundId || Id.uuid;
        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessEnd = new ProcessEnd();
        node.name = Config.PROCESS_NEW_END_NAME + ' ' + ElementFactoryBase.getDateStr();
        node.description = Config.PROCESS_NEW_END_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        node.tracesFrom = [];
        node.tracesTo = [];
        return this.dataService.createElement(node, true, compoundId)
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => node);
    }

}
