import { Config } from '../../../../config/config';
import { Proxy } from '../../../../model/support/proxy';
import { CEGCauseNode } from '../../../../model/CEGCauseNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { CEGEffectNode } from '../../../../model/CEGEffectNode';
import { CEGNode } from '../../../../model/CEGNode';
import { IContainer } from '../../../../model/IContainer';
import { SpecmateDataService } from '../../../../services/specmate-data.service';
import { Id } from '../../../../util/Id';
import { Type } from '../../../../util/Type';
import { Url } from '../../../../util/Url';
import { CreateTool } from "./create-tool";


export class ConnectionTool extends CreateTool<CEGNode | CEGConnection> {
    name: string = 'Add Connection';
    icon: string = 'sitemap';
    color: string = 'primary';
    cursor: string = 'crosshair';
    selectedElements: (CEGNode | CEGConnection)[] = [];

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    private get firstNode(): CEGNode {
        return this.selectedElements[0] as CEGNode;
    }

    private get secondNode(): CEGNode {
        return this.selectedElements[1] as CEGNode;
    }

    private get firstNodePresent(): boolean {
        return this.nodePresent(0);
    }

    private get secondNodePresent(): boolean {
        return this.nodePresent(1);
    }

    private get present(): boolean {
        return this.firstNodePresent && this.secondNodePresent;
    }

    private nodePresent(index: number): boolean {
        return this.selectedElements[index] != null && this.selectedElements[index] !== undefined;
    }

    private get isValid(): boolean {
        return this.present && this.firstNode !== this.secondNode;
        // && (Type.is(this.firstNode, CEGCauseNode) && Type.is(this.secondNode, CEGEffectNode))
        // || (Type.is(this.secondNode, CEGCauseNode) && Type.is(this.firstNode, CEGEffectNode));
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
            } else if(this.selectedElements.length === 1 && this.selectedElements[0] !== element) {
                this.selectedElements[1] = element;
            }
        }
        if (this.isValid) {
            return this.createNewConnection(this.selectedElements[0] as CEGNode, this.selectedElements[1] as CEGNode);
        }
        return Promise.resolve();
    }

    private get isConnectionSelected(): boolean {
        return this.selectedElements.length === 1 && Type.is(this.selectedElements[0], CEGConnection);
    }

    private createNewConnection(e1: CEGNode, e2: CEGNode): Promise<void> {
        return this.dataService.readContents(this.parent.url, true).then((contents: IContainer[]) => {
            let siblingConnections: CEGConnection[] = contents.filter((element: IContainer) => Type.is(element, CEGConnection)) as CEGConnection[];
            let alreadyExists: boolean = siblingConnections.some((connection: CEGConnection) => connection.source.url === e1.url && connection.target.url === e2.url);
            if (!alreadyExists) {
                return this.getNewId(Config.CEG_CONNECTION_BASE_ID);
            }
            return Promise.resolve(undefined);
        }).then((id: string) => {
            if (id) {
                let connection = this.connectionFactory(id, e1, e2);
                this.createAndSelect(connection);
            }
        });
    }

    private connectionFactory(id: string, e1: CEGNode, e2: CEGNode): CEGConnection {
        let url: string = Url.build([this.parent.url, id]);
        let connection: CEGConnection = new CEGConnection();
        connection.name = Config.CEG_NEW_CONNECTION_NAME;
        connection.description = Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.negate = false;
        connection.source = new Proxy();
        connection.source.url = e1.url;
        connection.target = new Proxy();
        connection.target.url = e2.url;
        let proxy: Proxy = new Proxy();
        proxy.url = connection.url;
        if (!e1.outgoingConnections) {
            e1.outgoingConnections = [];
        }
        if (!e2.incomingConnections) {
            e2.incomingConnections = [];
        }
        e1.outgoingConnections.push(proxy);
        e2.incomingConnections.push(proxy);
        return connection;
    }
}
