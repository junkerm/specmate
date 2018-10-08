import { ToolBase } from './tool-base';
import { IContainer } from '../../../../../../../model/IContainer';

export interface IDoubleClickTool extends ToolBase {
    dblClick(component: IContainer, event: MouseEvent): Promise<void>;
}
