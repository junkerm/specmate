import { ITool } from './i-tool';
import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';

export class MoveTool implements ITool<CEGNode | CEGConnection> {
    name: string = 'Move & Select';
    icon: string = 'arrows';
    color: string = 'primary';
    cursor: string = 'move';
    selectedElements: (CEGNode | CEGConnection)[] = [];

    constructor() { }

    activate(): void {
        this.selectedElements = [];
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent): void { }

    select(element: CEGNode | CEGConnection): void {
        this.selectedElements[0] = element;
    }
}
