import { Config } from '../../../../config/config';
import { Proxy } from '../../../../model/support/proxy';
import { IContainer } from '../../../../model/IContainer';
import { SpecmateDataService } from '../../../../services/data/specmate-data.service';
import { Id } from '../../../../util/Id';
import { Type } from '../../../../util/Type';
import { Url } from '../../../../util/Url';
import { CreateTool } from "./create-tool";
import { IModelConnection } from '../../../../model/IModelConnection';
import { IModelNode } from '../../../../model/IModelNode';
import { TypeAwareToolBase } from './type-aware-tool-base';


export abstract class ConnectionToolBase<T extends IModelConnection> extends CreateTool {
    color: string = 'primary';
    cursor: string = 'crosshair';
    done: boolean = false;
    selectedElements: (IModelNode | T)[] = [];

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super(parent, dataService);
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

    activate(): void {
        this.selectedElements = [];
        this.done = false;
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent): Promise<void> {
        return Promise.resolve();
    }

    select(element: IModelNode | T): Promise<void> {
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
                return Id.uuid;
            }
            return Promise.resolve(undefined);
        }).then((id: string) => {
            if (id) {
                let connection = this.createConnection(id, e1, e2);
                this.createAndSelect(connection);
            }
        });
    }

    protected abstract createConnection(id: string, e1: IModelNode, e2: IModelNode): T;
}
