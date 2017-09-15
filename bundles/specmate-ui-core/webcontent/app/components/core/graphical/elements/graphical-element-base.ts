import { Type } from "../../../../util/Type";
import { IContainer } from "../../../../model/IContainer";

export abstract class GraphicalElementBase<T extends IContainer> {
    public abstract get element(): T;
    public abstract get nodeType():  {className: string};

    public get isVisible(): boolean {
        return Type.is(this.element, this.nodeType);
    }
}