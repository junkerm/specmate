import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Id } from "../../../../util/Id";
import { ITool } from "./i-tool";
import { TypeAwareToolBase } from "./type-aware-tool-base";

export abstract class CreateTool extends TypeAwareToolBase {
    abstract name: string;
    abstract icon: string;
    abstract color: string;
    abstract cursor: string;
    abstract done: boolean;

    abstract click(event: MouseEvent): Promise<void>;
    abstract select(element: IContainer): Promise<void>;

    selectedElements: IContainer[];
    
    activate(): void {
        this.done = false;
        this.selectedElements = [];
    }
    
    deactivate(): void {
        this.selectedElements = [];
    }

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super();
        this.selectedElements = [];
    }

    protected createAndSelect(element: IContainer): Promise<void> {
        return this.dataService.createElement(element, true, Id.uuid).then(() => {
            this.selectedElements = [element];
            this.done = true;
        });
    }
}