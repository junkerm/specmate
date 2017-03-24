import { Config } from '../../../../config/config';
import { CEGCauseNode } from '../../../../model/CEGCauseNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { CEGEffectNode } from '../../../../model/CEGEffectNode';
import { CEGNode } from '../../../../model/CEGNode';
import { IContainer } from '../../../../model/IContainer';
import { SpecmateDataService } from '../../../../services/specmate-data.service';
import { Id } from '../../../../util/Id';
import { Type } from '../../../../util/Type';
import { Url } from '../../../../util/Url';
import { ITool } from './ITool';


export class ConnectionTool implements ITool {
    name: string = 'Add Connection';
    icon: string = 'sitemap';
    color: string = 'primary';
    selectedElements: (CEGNode | CEGConnection)[] = [];

    constructor(private parent: IContainer, private dataService: SpecmateDataService) { }

    get firstNode(): CEGNode {
        return this.selectedElements[0] as CEGNode;
    }

    get secondNode(): CEGNode {
        return this.selectedElements[1] as CEGNode;
    }

    get firstNodePresent(): boolean {
        return this.nodePresent(0);
    }

    get secondNodePresent(): boolean {
        return this.nodePresent(1);
    }

    get present(): boolean {
        return this.firstNodePresent && this.secondNodePresent;
    }

    nodePresent(index: number): boolean {
        return this.selectedElements[index] != null && this.selectedElements[index] !== undefined;
    }

    get isValid(): boolean {
        return this.present && this.firstNode !== this.secondNode;
        // && (Type.is(this.firstNode, CEGCauseNode) && Type.is(this.secondNode, CEGEffectNode))
        // || (Type.is(this.secondNode, CEGCauseNode) && Type.is(this.firstNode, CEGEffectNode));
    }

    private readSiblingConnections(): Promise<CEGConnection[]> {
        return this.dataService.readContents(this.parent.url, true)
            .then((contents: CEGConnection[]) => {
                return contents.filter((element: CEGNode | CEGConnection) => Type.is(element, CEGConnection));
            });
    }

    private getNewConnectionId(): Promise<string> {
        return this.readSiblingConnections()
            .then((contents: IContainer[]) => {
                return Id.generate(contents, Config.CEG_CONNECTION_BASE_ID);
            });
    }

    activate(): void {
        this.selectedElements = [];
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent): Promise<void> {
        return Promise.resolve();
    }

    select(element: CEGNode | CEGConnection): Promise<void> {
        if (this.isConnectionSelected) {
            this.selectedElements = [];
        }
        if (Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode)) {
            if (this.selectedElements.length === 2 || this.selectedElements.length === 0) {
                this.selectedElements = [];
                this.selectedElements[0] = element;
            } else {
                this.selectedElements[1] = element;
            }
        }
        if (this.isValid) {
            return this.addConnection(this.selectedElements[0] as CEGNode, this.selectedElements[1] as CEGNode);
        }
        return Promise.resolve();
    }

    private get isConnectionSelected(): boolean {
        return this.selectedElements.length === 1 && Type.is(this.selectedElements[0], CEGConnection);
    }

    private addConnection(e1: CEGNode, e2: CEGNode): Promise<void> {
        return this.getNewConnectionId()
            .then((id: string) => {
                let url: string = Url.build([this.parent.url, id]);

                let connection: CEGConnection = new CEGConnection();
                connection.name = Config.CEG_NEW_CONNECTION_NAME;
                connection.description = Config.CEG_NEW_CONNECTION_DESCRIPTION;
                connection.id = id;
                connection.url = url;
                connection.source = { url: e1.url };
                connection.source['___proxy'] = true;
                connection.target = { url: e2.url };
                connection.target['___proxy'] = true;

                this.dataService.createElement(connection, true);
                this.selectedElements = [connection];
            });
    }
}
