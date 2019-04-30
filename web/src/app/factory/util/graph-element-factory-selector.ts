import { CEGConnection } from '../../model/CEGConnection';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { IModelConnection } from '../../model/IModelConnection';
import { IModelNode } from '../../model/IModelNode';
import { ISpecmatePositionableModelObject } from '../../model/ISpecmatePositionableModelObject';
import { ProcessConnection } from '../../model/ProcessConnection';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessStep } from '../../model/ProcessStep';
import { SpecmateDataService } from '../../modules/data/modules/data-service/services/specmate-data.service';
import { CEGConnectionFactory } from '../ceg-connection-factory';
import { CEGNodeFactory } from '../ceg-node-factory';
import { ConnectionElementFactoryBase } from '../connection-element-factory-base';
import { PositionableElementFactoryBase } from '../positionable-element-factory-base';
import { ProcessConnectionFactory } from '../process-connection-factory';
import { ProcessDecisionFactory } from '../process-decision-factory';
import { ProcessEndFactory } from '../process-end-factory';
import { ProcessStartFactory } from '../process-start-factory';
import { ProcessStepFactory } from '../process-step-factory';

export type Coords =  {x: number, y: number};
export class GraphElementFactorySelector {
    public static getNodeFactory(template: IContainer, coords: Coords, dataService: SpecmateDataService):
                        PositionableElementFactoryBase<ISpecmatePositionableModelObject> {
        switch (template.className) {
            case CEGNode.className:
                return new CEGNodeFactory(coords, dataService);

            case ProcessStart.className:
                return new ProcessStartFactory(coords, dataService);

            case ProcessEnd.className:
                return new ProcessEndFactory(coords, dataService);

            case ProcessDecision.className:
                return new ProcessDecisionFactory(coords, dataService);

            case ProcessStep.className:
                return new ProcessStepFactory(coords, dataService);
        }
        throw new Error('Unkown Nodetype "' + template.className + '"');
    }

    public static getConnectionFactory(template: IContainer, source: IModelNode, target: IModelNode, dataService: SpecmateDataService):
                        ConnectionElementFactoryBase<IModelConnection> {
        switch (template.className) {
            case CEGConnection.className:
                return new CEGConnectionFactory(source, target, dataService);

            case ProcessConnection.className:
                return new ProcessConnectionFactory(source, target, dataService);
        }
        throw new Error('Unknown Connectiontype "' +  template.className + '"');
    }
}
