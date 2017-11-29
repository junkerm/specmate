import { Type } from "../../../../util/Type";
import { IContainer } from "../../../../model/IContainer";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";

export abstract class GraphicalElementBase<T extends IContainer> {
    public abstract get element(): T;
    public abstract get nodeType():  {className: string};

    constructor(private selectedElementService: SelectedElementService) { }

    public get isVisible(): boolean {
        return Type.is(this.element, this.nodeType);
    }

    public get selected(): boolean {
        return this.selectedElementService.isSelected(this.element);
    }
}