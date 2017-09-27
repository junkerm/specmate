import { ElementProvider } from '../providers/element-provider';
import { IContainer } from '../../../../model/IContainer';
import { ITool } from './i-tool';

export abstract class TypeAwareToolBase implements ITool {
    abstract selectedElements: IContainer[];

    abstract activate(): void;
    abstract deactivate(): void;
    abstract click(event: MouseEvent): Promise<void>;
    abstract select(element: IContainer): Promise<void>;

    protected abstract get modelType(): {className: string};
    
    public abstract name: string;
    public abstract icon: string;
    public abstract color: string;
    public abstract cursor: string;
    public abstract done: boolean;

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