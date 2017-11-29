import { Type } from "../../../../util/Type";
import { IContainer } from "../../../../model/IContainer";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";
import { ValidationService } from "../../../../services/validation/validation.service";

export abstract class GraphicalElementBase<T extends IContainer> {
    public abstract get element(): T;
    public abstract get nodeType():  {className: string};

    constructor(private selectedElementService: SelectedElementService, private validationService: ValidationService) { }

    public get isVisible(): boolean {
        return Type.is(this.element, this.nodeType);
    }

    public get selected(): boolean {
        return this.selectedElementService.isSelected(this.element);
    }

    public get isValid(): boolean {
        return this.validationService.isValid(this.element);
    }
}