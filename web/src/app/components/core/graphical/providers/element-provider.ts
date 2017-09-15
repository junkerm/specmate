import { IContainer } from "../../../../model/IContainer";
import { Type } from "../../../../util/Type";
import { CEGModel } from "../../../../model/CEGModel";
import { CEGNode } from "../../../../model/CEGNode";
import { Arrays } from "../../../../util/Arrays";
import { CEGConnection } from "../../../../model/CEGConnection";

export class ElementProvider {
    
    constructor(private _elements: IContainer[], private modelType: {className: string}) { }
    
    public get nodes(): IContainer[] {
        return this.getElementsOfTypes(this.nodeTypes);
    }

    public get connections(): IContainer[] {
        return this.getElementsOfTypes(this.connectionTypes);
    }

    private getElementsOfTypes(types: {className: string}[]): IContainer[] {
        return this._elements.filter((element: IContainer) => Arrays.contains(types.map((type: {className: string}) => Type.of(type)), Type.of(element)));
    }

    private get nodeTypes(): {className: string}[] {
        if(Type.is(this.modelType, CEGModel)) {
            return [CEGNode];
        }
    }

    private get connectionTypes(): {className: string}[] {
        if(Type.is(this.modelType, CEGModel)) {
            return [CEGConnection];
        }
    }
}