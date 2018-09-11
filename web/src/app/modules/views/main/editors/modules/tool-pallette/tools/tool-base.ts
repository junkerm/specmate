import { IContainer } from '../../../../../../../model/IContainer';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';

export abstract class ToolBase {
    protected abstract icon: string;
    protected abstract color: string;
    public abstract name: string;
    public abstract cursor: string;

    public abstract done: boolean;
    public abstract sticky: boolean;

    public abstract selectedElements: IContainer[];

    public abstract activate(): void;
    public abstract deactivate(): void;

    public abstract click(event: MouseEvent, zoom: number): Promise<void>;
    public abstract select(element: IContainer, evt?: MouseEvent): Promise<void>;

    constructor(protected selectedElementService: SelectedElementService) { }
}
