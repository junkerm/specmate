import { Config } from '../config/config';
import { CEGModel } from '../model/CEGModel';
import { IContainer } from '../model/IContainer';
import { ElementFactoryBase } from './element-factory-base';
import { ModelFactoryBase } from './model-factory-base';

export class CEGModelFactory extends ModelFactoryBase {
    protected get simpleModel(): IContainer {
        return new CEGModel();
    }

    protected get name(): string {
        return Config.CEG_NEW_MODEL_NAME + ' ' + ElementFactoryBase.getDateStr();
    }

    protected get description(): string {
        return Config.CEG_NEW_MODEL_DESCRIPTION;
    }
}
