import { ITool } from "./i-tool";
import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { SpecmateDataService } from "../../../../services/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Id } from "../../../../util/Id";

export abstract class CreateTool<T extends IContainer> implements ITool<T> {
    abstract name: string;
    abstract icon: string;
    abstract color: string;

    abstract click(event: MouseEvent): void;
    abstract select(element: T): void;

    selectedElements: T[];
    
    activate(): void {
        this.selectedElements = [];
    }
    
    deactivate(): void {
        this.selectedElements = [];
    }

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    protected getNewId(idBase: string): Promise<string> {
        return this.dataService.readContents(this.parent.url, true).then((contents: IContainer[]) => Id.generate(contents, idBase));
    }

    protected createAndSelect(element: T): void {
        this.dataService.createElement(element, true);
        this.selectedElements = [element];
    }
}