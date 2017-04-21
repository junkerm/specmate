import {Type} from '../../../util/Type';
import { CEGNodeDetails } from './ceg-node-details.component';
import { ChangeDetectionStrategy, ViewChildren, QueryList, ViewChild, SimpleChange, Component, Input, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';

import { IContainer } from '../../../model/IContainer';
import { CEGModel } from '../../../model/CEGModel';
import { CEGNode } from '../../../model/CEGNode';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { CEGConnection } from '../../../model/CEGConnection';

import { ITool } from './tools/i-tool';
import { DeleteTool } from './tools/delete-tool';
import { ConnectionTool } from './tools/connection-tool';
import { MoveTool } from './tools/move-tool';
import { NodeTool } from './tools/node-tool';

import { SpecmateDataService } from "../../../services/specmate-data.service";
import { CEGGraphicalConnection } from "./ceg-graphical-connection.component";


@Component({
    moduleId: module.id,
    selector: 'ceg-editor',
    templateUrl: 'ceg-editor.component.html',
    styleUrls: ['ceg-editor.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class CEGEditor implements OnInit {

    @ViewChildren(CEGNodeDetails)
    private nodeDetails: QueryList<CEGNodeDetails>;


    private _graphicalConnections: QueryList<CEGGraphicalConnection>;

    @ViewChildren(CEGGraphicalConnection)
    private set graphicalConnections(graphicalConnections: QueryList<CEGGraphicalConnection>) {
        this._graphicalConnections = graphicalConnections;
        this.changeDetectorRef.detectChanges();
    }

    @Input()
    private model: CEGModel;

    @Input()
    private contents: IContainer[];

    private get editorDimensions(): {width: number, height: number} {
        let dynamicWidth: number = 0;
        let dynamicHeight: number = 0;
        
        let nodes: CEGNode[] = this.contents.filter((element: IContainer) => {
            return Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode);
        }) as CEGNode[];

        for(let i = 0; i < nodes.length; i++) {
            let nodeX: number = nodes[i].x + (Config.CEG_NODE_WIDTH);
            if(dynamicWidth < nodeX) {
                dynamicWidth = nodeX;
            }

            let nodeY: number = nodes[i].y + (Config.CEG_NODE_HEIGHT);
            if(dynamicHeight < nodeY) {
                dynamicHeight = nodeY;
            }
        }
        return {width: Math.max(Config.CEG_EDITOR_WIDTH, dynamicWidth), height: Math.max(Config.CEG_EDITOR_HEIGHT, dynamicHeight)};
    }

    private causeNodeType = CEGCauseNode;
    private nodeType = CEGNode;
    private effectNodeType = CEGEffectNode;
    private connectionType = CEGConnection;

    private tools: ITool<IContainer>[];
    private activeTool: ITool<IContainer>;

    constructor(private dataService: SpecmateDataService, private changeDetectorRef: ChangeDetectorRef) { }

    ngOnInit(): void {
        this.tools = [
            new MoveTool(),
            // new CauseNodeTool(this.container, this.contents, this.dataService),
            // new EffectNodeTool(this.container, this.contents, this.dataService),
            new NodeTool(this.model, this.dataService),
            new ConnectionTool(this.model, this.dataService),
            new DeleteTool(this.model, this.dataService)
        ];

        this.activate(this.tools[0]);
    }

    public get isValid(): boolean {
        if (!this.nodeDetails) {
            return true;
        }
        return !this.nodeDetails.some((details: CEGNodeDetails) => !details.isValid);
    }

    private isValidElement(element: IContainer): boolean {
        let nodeDetail: CEGNodeDetails = this.nodeDetails.find((details: CEGNodeDetails) => details.element === element);
        if (!nodeDetail) {
            return true;
        }
        return nodeDetail.isValid;
    }

    private getGraphicalConnections(node: CEGNode): CEGGraphicalConnection[] {
        if (!this._graphicalConnections) {
            return [];
        }
        return this._graphicalConnections.filter((connection: CEGGraphicalConnection) => connection.connection.target.url === node.url);
    }

    private activate(tool: ITool<any>): void {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    }

    private isActive(tool: ITool<IContainer>): boolean {
        return this.activeTool === tool;
    }

    private get selectedNodes(): IContainer[] {
        if (this.activeTool) {
            return this.activeTool.selectedElements;
        }
        return [];
    }

    private get selectedNode(): IContainer {
        let selectedNodes = this.selectedNodes;
        if (selectedNodes.length > 0) {
            return selectedNodes[selectedNodes.length - 1];
        }
        return undefined;
    }

    private isSelected(element: IContainer) {
        return this.selectedNodes.indexOf(element) >= 0;
    }

    private select(element: IContainer): void {
        if (this.activeTool) {
            this.activeTool.select(element);
        }
    }

    private click(evt: MouseEvent): void {
        if (this.activeTool) {
            this.activeTool.click(evt);
        }
    }

    public reset(): void {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
    }
}
