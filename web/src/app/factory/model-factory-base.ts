import { ElementFactoryBase } from './element-factory-base';
import { CEGModel } from '../model/CEGModel';
import { Process } from '../model/Process';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';

export abstract class ModelFactoryBase extends ElementFactoryBase<CEGModel | Process> {
    
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<CEGModel | Process> {
        let element: IContainer = this.simpleModel;
        element.id = Id.uuid;
        element.url = Url.build([parent.url, element.id]);
        element.name = this.name;
        element.description = this.description;
    
        return this.dataService.createElement(element, true, Id.uuid)
            .then(() => commit ? this.dataService.commit('Create') : Promise.resolve())
            .then(() => element);
    }

    protected abstract get simpleModel(): IContainer;
    protected abstract get name(): string;
    protected abstract get description(): string;

}