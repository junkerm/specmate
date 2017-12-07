import { ToolBase } from './tool-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { ElementProvider } from '../../graphical-editor/providers/properties/element-provider';

export abstract class TypeAwareToolBase extends ToolBase {
    protected abstract get modelType(): {className: string};

    private _elementProvider: ElementProvider;
    private get elementProvider(): ElementProvider {
        if(!this._elementProvider && this.modelType) {
            this._elementProvider = new ElementProvider(this.modelType);
        }
        return this._elementProvider;
    }

    public isNode(element: IContainer): boolean {
        return this.elementProvider.isNode(element);
    }

    public isConnection(element: IContainer): boolean {
        return this.elementProvider.isConnection(element);
    }
}