import { IModelConnection } from '../model/IModelConnection';
import { ElementFactoryBase } from './element-factory-base';
import { IModelNode } from '../model/IModelNode';
import { SpecmateDataService } from '../modules/data/modules/data-service/services/specmate-data.service';
import { TranslateService } from '@ngx-translate/core';

export abstract class ConnectionElementFactoryBase<T extends IModelConnection> extends ElementFactoryBase<T> {
    constructor(protected source: IModelNode, protected target: IModelNode, dataService: SpecmateDataService) {
        super(dataService);
    }
}
