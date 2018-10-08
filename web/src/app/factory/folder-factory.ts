import { ModelFactoryBase } from './model-factory-base';
import { IContainer } from '../model/IContainer';
import { Folder } from '../model/Folder';
import { Config } from '../config/config';
import { ElementFactoryBase } from './element-factory-base';

export class FolderFactory extends ModelFactoryBase {
    protected get simpleModel(): IContainer {
        return new Folder();
    }

    protected get name(): string {
        return Config.FOLDER_NEW_NAME + ' ' + ElementFactoryBase.getDateStr();
    }

    protected get description(): string {
        return Config.FOLDER_NEW_DESCRIPTION;
    }
}
