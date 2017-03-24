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

    constructor(private parent: IContainer, private dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    activate(): void { }
    deactivate(): void { }
    click(event: MouseEvent): void { }

    select(element: CEGNode | CEGConnection): void {
        this.getConnections(element as CEGNode)
            .then((connections: IContainer[]) => {
                for (let i = 0; i < connections.length; i++) {
                    this.dataService.deleteElement(connections[i].url, true);
                }
            })
            .then(() => {
                this.dataService.deleteElement(element.url, true);
            });
    }

    private getConnections(node: CEGNode | CEGConnection): Promise<CEGConnection[]> {
        if (Type.is(node, CEGNode) || Type.is(node, CEGCauseNode) || Type.is(node, CEGEffectNode)) {
            return this.dataService.readContents(this.parent.url, true)
                .then((contents: IContainer[]) => {
                    let connections: CEGConnection[] = [];
                    for (let i = 0; i < contents.length; i++) {
                        let currentElement: IContainer = contents[i];
                        if (Type.is(currentElement, CEGConnection)) {
                            let currentConnection: CEGConnection = currentElement as CEGConnection;
                            if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                                connections.push(currentConnection);
                            }
                        }
                    }
                    return connections;
                });
        }
        return Promise.resolve([]);
    }
}
