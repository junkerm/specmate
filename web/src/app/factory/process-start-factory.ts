import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { ProcessStart } from '../model/ProcessStart';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { PositionableElementFactoryBase } from './positionable-element-factory-base';

export class ProcessStartFactory extends PositionableElementFactoryBase<ProcessStart> {
    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<ProcessStart> {
        compoundId = compoundId || Id.uuid;
        let id = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessStart = new ProcessStart();
        node.name = name || Config.PROCESS_NEW_START_NAME + ' ' + ElementFactoryBase.getDateStr();
        node.description = Config.PROCESS_NEW_START_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        node.tracesFrom = [];
        node.tracesTo = [];
        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }

}
