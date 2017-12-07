import { ModelFactoryBase } from './model-factory-base';
import { IContainer } from '../model/IContainer';
import { CEGModel } from '../model/CEGModel';
import { Config } from '../config/config';

export class CEGModelFactory extends ModelFactoryBase {
    protected get simpleModel(): IContainer {
        return new CEGModel();
    }

    protected get name(): string {
        return Config.CEG_NEW_MODEL_NAME;
    }

    protected get description(): string {
        return Config.CEG_NEW_MODEL_DESCRIPTION;
    }
}
