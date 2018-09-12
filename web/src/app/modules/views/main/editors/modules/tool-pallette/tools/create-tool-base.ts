import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { TypeAwareToolBase } from './type-aware-tool-base';

export abstract class CreateToolBase extends TypeAwareToolBase {
    abstract icon: string;
    abstract color: string;
    abstract cursor: string;
    abstract done: boolean;
    public sticky = true;

    public selectedElements: IContainer[];

    abstract click(event: MouseEvent, zoom: number): Promise<void>;
    abstract select(element: IContainer): Promise<void>;

    public activate(): void {
        this.done = false;
        this.selectedElements = [];
    }

    public deactivate(): void {
        this.selectedElements = [];
    }

    constructor(protected parent: IContainer,
    protected dataService: SpecmateDataService,
    selectedElementService: SelectedElementService) {
        super(selectedElementService);
        this.selectedElements = [];
    }
}
