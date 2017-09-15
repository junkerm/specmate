import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Id } from "../../../../util/Id";
import { ITool } from "./i-tool";

export abstract class CreateTool<T extends IContainer> implements ITool<T> {
    abstract name: string;
    abstract icon: string;
    abstract color: string;
    abstract cursor: string;
    abstract done: boolean;

    abstract click(event: MouseEvent): Promise<void>;
    abstract select(element: T): Promise<void>;

    selectedElements: T[];
    
    activate(): void {
        this.done = false;
        this.selectedElements = [];
    }
    
    deactivate(): void {
        this.selectedElements = [];
    }

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    protected getNewId(idBase: string): string {
        return Id.uuid;
    }

    protected createAndSelect(element: T): Promise<void> {
        return this.dataService.createElement(element, true, Id.uuid).then(() => {
            this.selectedElements = [element];
            this.done = true;
        });
    }
}