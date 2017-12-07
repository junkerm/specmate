import { ISpecmatePositionableModelObject } from '../model/ISpecmatePositionableModelObject';
import { ElementFactoryBase } from './element-factory-base';
import { SpecmateDataService } from '../modules/data/modules/data-service/services/specmate-data.service';

export abstract class PositionableElementFactoryBase<T extends ISpecmatePositionableModelObject> extends ElementFactoryBase<T> {

    constructor(protected coords: {x: number, y: number}, dataService: SpecmateDataService) {
        super(dataService);
    }

}