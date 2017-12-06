import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { CreateToolBase } from './create-tool-base';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';

export abstract class ConnectionToolBase<T extends IModelConnection> extends CreateToolBase {

    public color: string = 'primary';
    public cursor: string = 'crosshair';
    public done: boolean = false;
    public selectedElements: (IModelNode | T)[] = [];

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    private get firstNode(): IModelNode {
        return this.selectedElements[0] as IModelNode;
    }

    private get secondNode(): IModelNode {
        return this.selectedElements[1] as IModelNode;
    }

    private get firstNodePresent(): boolean {
        return this.nodePresent(0);
    }

    private get secondNodePresent(): boolean {
        return this.nodePresent(1);
    }

    private get present(): boolean {
        return this.firstNodePresent && this.secondNodePresent;
    }

    private nodePresent(index: number): boolean {
        return this.selectedElements[index] != null && this.selectedElements[index] !== undefined;
    }

    private get isValid(): boolean {
        return this.present && this.firstNode !== this.secondNode;
    }

    public activate(): void {
        this.selectedElements = [];
        this.done = false;
    }

    public deactivate(): void {
        this.selectedElements = [];
    }

    public click(event: MouseEvent, zoom: number): Promise<void> {
        return Promise.resolve();
    }

    public select(element: IModelNode | T): Promise<void> {
        if (this.isConnectionSelected) {
            this.selectedElements = [];
        }
        if (this.isNode(element)) {
            if (this.selectedElements.length === 2 || this.selectedElements.length === 0) {
                this.selectedElements = [];
                this.selectedElements[0] = element;
            } else if(this.selectedElements.length === 1 && this.selectedElements[0] !== element) {
                this.selectedElements[1] = element;
            }
            this.selectedElementService.select(element);
        }
        if (this.isValid) {
            return this.createNewConnection(this.selectedElements[0] as IModelNode, this.selectedElements[1] as IModelNode);
        }
        return Promise.resolve();
    }

    private get isConnectionSelected(): boolean {
        return this.selectedElements.length === 1 && this.isConnection(this.selectedElements[0]);
    }

    private createNewConnection(e1: IModelNode, e2: IModelNode): Promise<void> {
        return this.dataService.readContents(this.parent.url, true).then((contents: IContainer[]) => {
            let siblingConnections: T[] = contents.filter((element: IContainer) => this.isConnection(element)) as T[];
            let alreadyExists: boolean = siblingConnections.some((connection: T) => connection.source.url === e1.url && connection.target.url === e2.url);
            if (!alreadyExists) {
                return this.getFactory(e1, e2).create(this.parent, false)
                    .then((element: IModelConnection) => this.selectedElementService.select(element))
                    .then(() => this.done = true)
                    .then(() => Promise.resolve());
            }
            return Promise.resolve();
        });
    }

    protected abstract getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection>;
}
