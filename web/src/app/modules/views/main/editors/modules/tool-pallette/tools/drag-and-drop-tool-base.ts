import { ToolBase } from './tool-base';

export abstract class DragAndDropToolBase extends ToolBase {
    public abstract mouseDown(event: MouseEvent, zoom: number): Promise<void>;
    public abstract mouseDrag(event: MouseEvent, zoom: number): Promise<void>;
    public abstract mouseUp(event: MouseEvent, zoom: number): Promise<void>;
}
