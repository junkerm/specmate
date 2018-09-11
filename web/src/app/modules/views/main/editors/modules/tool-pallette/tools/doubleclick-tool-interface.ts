import { IContainer } from '../../../../../../../model/IContainer';
import { ToolBase } from './tool-base';

export interface IDoubleClickTool extends ToolBase {
    dblClick(component: IContainer, event: MouseEvent): Promise<void>;
}
