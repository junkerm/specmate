import { CEGModel } from '../model/CEGModel';
import { IContainer } from '../model/IContainer';
import { Process } from '../model/Process';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';

export abstract class ModelFactoryBase extends ElementFactoryBase<CEGModel | Process> {

    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<CEGModel | Process> {
        let element: IContainer = this.simpleModel;
        element.id = Id.uuid;
        element.url = Url.build([parent.url, element.id]);
        element.name = name || this.name;
        element.description = this.description;

        return this.dataService.createElement(element, true, compoundId)
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => element);
    }

    protected abstract get simpleModel(): IContainer;
    protected abstract get name(): string;
    protected abstract get description(): string;

}
