import { ITool } from './ITool';
import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';

export class MoveTool implements ITool {
    name: string = 'Move & Select';
    icon: string = 'arrows';
    color: string = 'primary';
    selectedElements: (CEGNode | CEGConnection)[] = [];

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
        return Promise.resolve();
    }
}
