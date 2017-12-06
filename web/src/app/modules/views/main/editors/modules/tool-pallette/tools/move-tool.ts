import { ToolBase } from "./tool-base";
import { CEGNode } from "../../../../../../../model/CEGNode";
import { CEGConnection } from "../../../../../../../model/CEGConnection";
import { SelectedElementService } from "../../../../../side/modules/selected-element/services/selected-element.service";

export class MoveTool extends ToolBase {
    name: string = 'Select';
    icon: string = 'arrows';
    color: string = 'primary';
    cursor: string = 'move';
    selectedElements: (CEGNode | CEGConnection)[] = [];
    done: boolean = false;

    constructor(selectedElementService: SelectedElementService) {
        super(selectedElementService);
    }

    activate(): void {
        this.selectedElements = [];
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent, zoom: number): Promise<void> {
        return Promise.resolve();
    }

    select(element: CEGNode | CEGConnection): Promise<void> {
        this.selectedElements[0] = element;
        this.selectedElementService.selectedElement = element;
        let blur = (<HTMLElement>document.activeElement).blur;
        if(blur) {
            (<HTMLElement>document.activeElement).blur();
        }
        return Promise.resolve();
    }
}
