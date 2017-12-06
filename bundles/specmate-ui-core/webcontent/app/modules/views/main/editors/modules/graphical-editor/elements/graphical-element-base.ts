import { IContainer } from "../../../../../../../model/IContainer";
import { SelectedElementService } from "../../../../../side/modules/selected-element/services/selected-element.service";
import { ValidationService } from "../../../../../../forms/modules/validation/services/validation.service";
import { Type } from "../../../../../../../util/type";

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