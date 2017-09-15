import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { ITool } from "./i-tool";

export class MoveTool implements ITool {
    name: string = 'Select';
    icon: string = 'arrows';
    color: string = 'primary';
    cursor: string = 'move';
    selectedElements: (CEGNode | CEGConnection)[] = [];
    done: boolean = false;

    constructor() { }

    activate(): void {
        this.selectedElements = [];
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent): Promise<void> {
        return Promise.resolve();
    }

    select(element: CEGNode | CEGConnection): Promise<void> {
        this.selectedElements[0] = element;
        let blur = (<HTMLElement>document.activeElement).blur;
        if(blur) {
            (<HTMLElement>document.activeElement).blur();
        }
        return Promise.resolve();
    }
}
