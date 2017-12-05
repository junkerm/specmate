import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Id } from "../../../../util/Id";
import { TypeAwareToolBase } from "./type-aware-tool-base";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";

export abstract class CreateToolBase extends TypeAwareToolBase {
    abstract name: string;
    abstract icon: string;
    abstract color: string;
    abstract cursor: string;
    abstract done: boolean;

    abstract click(event: MouseEvent, zoom: number): Promise<void>;
    abstract select(element: IContainer): Promise<void>;

    selectedElements: IContainer[];
    
    public activate(): void {
        this.done = false;
        this.selectedElements = [];
    }
    
    public deactivate(): void {
        this.selectedElements = [];
    }

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(selectedElementService);
        this.selectedElements = [];
    }
}