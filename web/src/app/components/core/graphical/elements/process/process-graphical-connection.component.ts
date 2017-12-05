import { Component, Input } from '@angular/core';
import { CEGConnection } from "../../../../../model/CEGConnection";
import { CEGNode } from "../../../../../model/CEGNode";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { Angles } from "../../util/angles";
import { Coords } from "../../util/coords";
import { GraphicalElementBase } from "../graphical-element-base";
import { IContainer } from "../../../../../model/IContainer";
import { ProcessConnection } from "../../../../../model/ProcessConnection";
import { GraphicalConnectionBase } from "../graphical-connection-base";
import { RectangularLineCoordsProvider } from "../coordinate-providers/rectangular-line-coords-provider";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { LineCoordsProviderBase } from "../coordinate-providers/line-coords-provider-base";
import { DiamondLineCoordsProvider } from "../coordinate-providers/diamond-line-coords-provider";
import { Type } from "../../../../../util/Type";
import { IModelNode } from "../../../../../model/IModelNode";
import { ProcessStart } from '../../../../../model/ProcessStart';
import { ProcessEnd } from '../../../../../model/ProcessEnd';
import { CircularLineCoordsProvider } from '../coordinate-providers/circular-line-coords-provider';
import { LineCoordinateProvider } from '../coordinate-providers/line-coordinate-provider';
import { SelectedElementService } from '../../../../../services/editor/selected-element.service';
import { ValidationService } from '../../../../../services/validation/validation.service';

@Component({
    moduleId: module.id,
    selector: '[process-graphical-connection]',
    templateUrl: 'process-graphical-connection.component.svg',
    styleUrls: ['process-graphical-connection.component.css']
})
export class ProcessGraphicalConnection extends GraphicalConnectionBase<ProcessConnection> {
    public nodeType: { className: string; } = ProcessConnection;

    constructor(selectedElementService: SelectedElementService, validationService: ValidationService) {
        super(selectedElementService, validationService);
    }

    public get showCondition(): boolean {
        return this.connection && this.connection.condition && this.connection.condition.length > 0;
    }

    public get conditionText(): string {
        if(this.showCondition) {
            return '[' + this.connection.condition + ']';
        }
        return '';
    }
}
