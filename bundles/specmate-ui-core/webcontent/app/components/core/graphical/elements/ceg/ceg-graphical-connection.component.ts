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
import { GraphicalConnectionBase } from "../graphical-connection-base";
import { RectangularLineCoordsProvider } from "../coordinate-providers/rectangular-line-coords-provider";
import { LineCoordsProviderBase } from "../coordinate-providers/line-coords-provider-base";
import { LineCoordinateProvider } from '../coordinate-providers/line-coordinate-provider';
import { SelectedElementService } from '../../../../../services/editor/selected-element.service';
import { ValidationService } from '../../../../../services/validation/validation.service';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-connection]',
    templateUrl: 'ceg-graphical-connection.component.svg',
    styleUrls: ['ceg-graphical-connection.component.css']
})
export class CEGGraphicalConnection extends GraphicalConnectionBase<CEGConnection> {
    public nodeType: { className: string; } = CEGConnection;

    constructor(selectedElementService: SelectedElementService, validationService: ValidationService) {
        super(selectedElementService, validationService);
    }

    private get isNegated(): boolean {
        return (this.connection.negate + '').toLowerCase() === 'true';
    }
}
