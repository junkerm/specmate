import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { IContainer } from '../../../../../../../model/IContainer';

export abstract class ToolBase {
    public abstract icon: string;
    public abstract style: string;
    public abstract color: string;
    public abstract name: string;
    public abstract isVertexTool: boolean;

    public abstract async perform(): Promise<any>;

    constructor(protected dataService: SpecmateDataService,
        protected selectedElementService: SelectedElementService,
        protected parent: IContainer) { }

    public get elementId(): string {
        return 'toolbar-' + name + '-button';
    }
}
