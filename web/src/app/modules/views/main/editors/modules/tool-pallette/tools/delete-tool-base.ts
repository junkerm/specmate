import { IContainer } from '../../../../../../../model/IContainer';
import { Id } from '../../../../../../../util/id';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { GraphTransformer } from '../util/graph-transformer';
import { TypeAwareToolBase } from './type-aware-tool-base';

export abstract class DeleteToolBase extends TypeAwareToolBase {

    public icon = 'eraser';
    public color = 'danger';
    public name = 'tools.delete';
    public style: string = undefined;
    public isVertexTool = true;

    private graphTransformer: GraphTransformer;

    public element: IContainer;

    constructor(parent: IContainer,
        dataService: SpecmateDataService,
        selectedElementService: SelectedElementService) {
        super(dataService, selectedElementService, parent);
        this.graphTransformer = new GraphTransformer(dataService, selectedElementService, parent);
    }

    public async perform(): Promise<any> {
        if (this.element === undefined) {
            throw new Error('Element undefined');
        }
        await this.graphTransformer.deleteElementAndDeselect(this.element, Id.uuid);
    }
}
