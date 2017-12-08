import { Component, Input } from '@angular/core';
import { DraggableElementBase } from '../../elements/draggable-element-base';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { Config } from '../../../../../../../../config/config';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';

@Component({
    moduleId: module.id.toString(),
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg',
    styleUrls: ['ceg-graphical-node.component.css']
})

export class CEGGraphicalNode extends DraggableElementBase<CEGNode> {
    public nodeType: { className: string; } = CEGNode;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.CEG_NODE_WIDTH,
            height: Config.CEG_NODE_HEIGHT
        };
    }

    @Input()
    node: CEGNode;

    public get element(): CEGNode {
        return this.node;
    }

    private get title(): string {
        return this.node.variable + ' ' + this.node.condition;
    }

    private get type(): string {
        return this.node.type;
    }

    constructor(
        protected dataService: SpecmateDataService,
        selectedElementService: SelectedElementService,
        validationService: ValidationService) {
        super(selectedElementService, validationService);
    }
}
