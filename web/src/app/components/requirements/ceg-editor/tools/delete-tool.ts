import { ITool } from './ITool';
import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { Type } from '../../../../util/Type';
import { SpecmateDataService } from '../../../../services/specmate-data.service';
import { IContainer } from '../../../../model/IContainer';
import { CEGEffectNode } from '../../../../model/CEGEffectNode';
import { CEGCauseNode } from '../../../../model/CEGCauseNode';

export class DeleteTool implements ITool {

    name: string = 'Delete';
    icon: string = 'trash';
    color: string = 'danger';

    selectedElements: (CEGNode | CEGConnection)[];

    constructor(private contents: IContainer[], private dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    activate(): void { }
    deactivate(): void { }
    click(event: MouseEvent): void { }

    select(element: CEGNode | CEGConnection): void {
        if (this.confirm()) {
            this.delete(element);
        }
    }

    confirm(): boolean {
        return true;
    }

    private delete(element: CEGNode | CEGConnection): void {
        if (Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode)) {
            let connections: CEGConnection[] = this.getConnections(element as CEGNode);
            for (let i = 0; i < connections.length; i++) {
                this.dataService.removeElement(connections[i]);
            }
        }
        this.dataService.removeElement(element);
    }

    private getConnections(node: CEGNode): CEGConnection[] {
        let connections: CEGConnection[] = [];
        for (let i = 0; i < this.contents.length; i++) {
            let currentElement: IContainer = this.contents[i];
            if (Type.is(currentElement, CEGConnection)) {
                let currentConnection: CEGConnection = currentElement as CEGConnection;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    }
}
