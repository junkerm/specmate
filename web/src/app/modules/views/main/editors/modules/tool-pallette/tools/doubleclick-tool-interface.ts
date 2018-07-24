import { ToolBase } from './tool-base';
import { IContainer } from '../../../../../../../model/IContainer';

export interface DoubleClickToolInterface extends ToolBase {
    dblClick(component: IContainer, event: MouseEvent): Promise<void>;
}
