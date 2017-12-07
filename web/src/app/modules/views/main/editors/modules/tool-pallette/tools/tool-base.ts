import { IContainer } from '../../../../../../../model/IContainer';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';

export abstract class ToolBase {
    protected abstract name: string;
    protected abstract icon: string;
    protected abstract color: string;
    public abstract cursor: string;

    public abstract done: boolean;

    public abstract selectedElements: IContainer[];

    public abstract activate(): void;
    public abstract deactivate(): void;

    public abstract click(event: MouseEvent, zoom: number): Promise<void>;
    public abstract select(element: IContainer): Promise<void>;

    constructor(protected selectedElementService: SelectedElementService) { }
}