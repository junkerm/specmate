import { OnDestroy } from '@angular/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { Type } from '../../../../../../../util/type';
import { ValidationService } from '../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { Square } from '../util/area';

export abstract class GraphicalElementBase<T extends IContainer> implements OnDestroy {
    public abstract get element(): T;
    public abstract get nodeType():  {className: string};

    constructor(protected selectedElementService: SelectedElementService,
                private validationService: ValidationService,
                protected multiselectionService: MultiselectionService) {
        multiselectionService.announceComponent(this);
    }

    public ngOnDestroy() {
        this.multiselectionService.retractComponent(this);
    }

    public get isVisible(): boolean {
        return Type.is(this.element, this.nodeType);
    }

    public get selected(): boolean {
        return this.selectedElementService.isSelected(this.element);
    }

    public get isValid(): boolean {
        return this.validationService.isValid(this.element);
    }

    public abstract isInSelectionArea(area: Square): boolean;
}
