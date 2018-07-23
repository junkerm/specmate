import { ToolBase } from './tool-base';

export interface KeyboardToolInterface extends ToolBase {
    keydown(evt: KeyboardEvent): Promise<void>;
}
