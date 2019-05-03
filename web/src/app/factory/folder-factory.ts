import { Config } from '../config/config';
import { Folder } from '../model/Folder';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';

export class FolderFactory extends ElementFactoryBase<Folder> {

    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<Folder> {
        let element: Folder = new Folder();
        element.id = Id.uuid;
        element.url = Url.build([parent.url, element.id]);
        element.name = name || Config.FOLDER_NEW_NAME + ' ' + ElementFactoryBase.getDateStr();
        element.description = Config.FOLDER_NEW_DESCRIPTION;

        return this.dataService.createElement(element, true, compoundId)
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => element);
    }
}
