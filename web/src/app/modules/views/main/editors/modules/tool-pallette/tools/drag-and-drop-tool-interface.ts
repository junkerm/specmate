import { ToolBase } from './tool-base';

export interface DragAndDropToolInterface extends ToolBase {
    mouseDown(event: MouseEvent, zoom: number): Promise<void>;
    mouseDrag(event: MouseEvent, zoom: number): Promise<void>;
    mouseUp(event: MouseEvent, zoom: number): Promise<void>;
}
