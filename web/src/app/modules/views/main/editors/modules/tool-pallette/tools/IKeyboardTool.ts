import { ToolBase } from './tool-base';

export interface IKeyboardTool extends ToolBase {
    keydown(evt: KeyboardEvent): Promise<void>;
}
