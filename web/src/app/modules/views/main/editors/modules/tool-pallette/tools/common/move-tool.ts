import { ToolBase } from '../tool-base';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';

// TODO Replaced by SelectTool => Can be removed
export class MoveTool extends ToolBase {

    public icon = 'arrows';
    public color = 'primary';
    public cursor = 'move';
    public name = 'tools.move';
    public selectedElements: (CEGNode | CEGConnection)[] = [];
    public done = false;

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
        if (blur) {
            (<HTMLElement>document.activeElement).blur();
        }
        return Promise.resolve();
    }
}
