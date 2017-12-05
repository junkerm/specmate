import { ElementFactoryBase } from "./element-factory-base";
import { IPositionable } from "../model/IPositionable";
import { IContainer } from "../model/IContainer";
import { SpecmateDataService } from "../services/data/specmate-data.service";
import { ISpecmatePositionableModelObject } from "../model/ISpecmatePositionableModelObject";

export abstract class PositionableElementFactoryBase<T extends ISpecmatePositionableModelObject> extends ElementFactoryBase<T> {

    constructor(protected coords: {x: number, y: number}, dataService: SpecmateDataService) {
        super(dataService);
    }

}