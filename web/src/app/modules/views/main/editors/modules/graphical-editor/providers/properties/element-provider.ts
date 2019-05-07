import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { IContainer } from '../../../../../../../../model/IContainer';
import { ProcessConnection } from '../../../../../../../../model/ProcessConnection';
import { ProcessDecision } from '../../../../../../../../model/ProcessDecision';
import { ProcessEnd } from '../../../../../../../../model/ProcessEnd';
import { ProcessStart } from '../../../../../../../../model/ProcessStart';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { Arrays } from '../../../../../../../../util/arrays';
import { Type } from '../../../../../../../../util/type';
import { ProviderBase } from './provider-base';

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
        if (!this._elements) {
            return [];
        }
        return this._elements.filter((element: IContainer) => this.isOfTypes(element, types));
    }

    private get nodeTypes(): {className: string}[] {
        if (this.isCEGModel) {
            return [CEGNode];
        } else if (this.isProcessModel) {
            return [ProcessStep, ProcessDecision, ProcessStart, ProcessEnd];
        }
    }

    private get connectionTypes(): {className: string}[] {
        if (this.isCEGModel) {
            return [CEGConnection];
        } else if (this.isProcessModel) {
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
