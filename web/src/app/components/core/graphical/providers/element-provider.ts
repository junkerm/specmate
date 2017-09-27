import { IContainer } from "../../../../model/IContainer";
import { Type } from "../../../../util/Type";
import { CEGModel } from "../../../../model/CEGModel";
import { CEGNode } from "../../../../model/CEGNode";
import { Arrays } from "../../../../util/Arrays";
import { CEGConnection } from "../../../../model/CEGConnection";
import { ProviderBase } from "./provider-base";
import { Process } from "../../../../model/Process";
import { ProcessDecision } from "../../../../model/ProcessDecision";
import { ProcessStep } from "../../../../model/ProcessStep";
import { ProcessConnection } from "../../../../model/ProcessConnection";
import { ProcessStart } from "../../../../model/ProcessStart";
import { ProcessEnd } from "../../../../model/ProcessEnd";

export class ElementProvider extends ProviderBase {

    constructor(type: {className: string}, private _elements?: IContainer[]) {
        super(type);
    }
    
    public get nodes(): IContainer[] {
        return this.getElementsOfTypes(this.nodeTypes);
    }

    public get connections(): IContainer[] {
        return this.getElementsOfTypes(this.connectionTypes);
    }

    private getElementsOfTypes(types: {className: string}[]): IContainer[] {
        return this._elements.filter((element: IContainer) => this.isOfTypes(element, types));
    }

    private get nodeTypes(): {className: string}[] {
        if(Type.is(this.modelType, CEGModel)) {
            return [CEGNode];
        } else if(Type.is(this.modelType, Process)) {
            return [ProcessStep, ProcessDecision, ProcessStart, ProcessEnd];
        }
    }

    private get connectionTypes(): {className: string}[] {
        if(Type.is(this.modelType, CEGModel)) {
            return [CEGConnection];
        } else if(Type.is(this.modelType, Process)) {
            return [ProcessConnection];
        }
    }

    public isNode(element: IContainer): boolean {
        return Arrays.contains(this.nodeTypes.map((type: {className: string}) => Type.of(type)), Type.of(element));
    }

    public isConnection(element: IContainer): boolean {
        return Arrays.contains(this.connectionTypes.map((type: {className: string}) => Type.of(type)), Type.of(element));
    }

    private isOfTypes(element: IContainer, types: {className: string}[]): boolean {
        return Arrays.contains(types.map((type: {className: string}) => Type.of(type)), Type.of(element));
    }
}