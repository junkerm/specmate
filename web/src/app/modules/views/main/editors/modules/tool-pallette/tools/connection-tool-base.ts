import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { CreateToolBase } from './create-tool-base';

export abstract class ConnectionToolBase<T extends IModelConnection> extends CreateToolBase {

    public color = 'primary';
    public isVertexTool = false;

    public source: IModelNode;
    public target: IModelNode;

    public async perform(): Promise<IModelConnection> {
        if (this.source === undefined || this.target === undefined) {
            throw new Error('Source or target undefined');
        }
        return await this.createNewConnection();
    }

    private async createNewConnection(): Promise<IModelConnection> {
        const contents = await this.dataService.readContents(this.parent.url, true);
        let siblingConnections: T[] = (contents.filter((element: IContainer) => this.isConnection(element)) as T[]);
        let alreadyExists: boolean =
            siblingConnections.some((connection: T) =>
                connection.source.url === this.source.url && connection.target.url === this.target.url);
        if (!alreadyExists) {
            const factory = this.getFactory(this.source, this.target);
            const connection = await factory.create(this.parent, false);
            this.selectedElementService.select(connection);
            return connection;
        }
    }

    protected abstract getFactory(e1: IModelNode, e2: IModelNode): ElementFactoryBase<IModelConnection>;
}
