import { ProviderBase } from './provider-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { Type } from '../../../../../../../../util/type';
import { CEGNode } from '../../../../../../../../model/CEGNode';

export class HiddenFieldsProvider extends ProviderBase {

    constructor(element: IContainer) {
        super(element);
    }

    private get element(): IContainer {
        return this.modelType as IContainer;
    }

    public get hiddenFields(): string[] {
        if (this.isCEGNodeWithoutIncomingConnections) {
            return ['type'];
        }
        return [];
    }

    private get isCEGNodeWithoutIncomingConnections(): boolean {
        return Type.is(this.element, CEGNode) &&
            (!(this.element as CEGNode).incomingConnections || (this.element as CEGNode).incomingConnections.length <= 1);
    }
}
