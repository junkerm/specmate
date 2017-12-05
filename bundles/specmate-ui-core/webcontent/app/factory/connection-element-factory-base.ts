import { ElementFactoryBase } from "./element-factory-base";
import { IModelConnection } from "../model/IModelConnection";
import { ISpecmateModelObject } from "../model/ISpecmateModelObject";
import { SpecmateDataService } from "../services/data/specmate-data.service";
import { IModelNode } from "../model/IModelNode";

export abstract class ConnectionElementFactoryBase<T extends IModelConnection> extends ElementFactoryBase<T> {
    constructor(protected source: IModelNode, protected target: IModelNode, dataService: SpecmateDataService) {
        super(dataService);
    }
}