import {CEGNodeDetails} from './ceg-node-details.component';
import {ViewChild, SimpleChange,  Component,  Input,  OnInit} from '@angular/core';
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


@Component({
    moduleId: module.id,
    selector: 'ceg-editor',
    templateUrl: 'ceg-editor.component.html'
})
export class CEGEditor implements OnInit {

    @ViewChild(CEGNodeDetails)
    private nodeDetails: CEGNodeDetails;

    @Input()
    private model: CEGModel;

    @Input()
    private contents: IContainer[];

    private editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    private causeNodeType = CEGCauseNode;
    private nodeType = CEGNode;
    private effectNodeType = CEGEffectNode;
    private connectionType = CEGConnection;

    private tools: ITool<IContainer>[];
    private activeTool: ITool<IContainer>;

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

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

    public update(): void {
        this.nodeDetails.update();
    }

    public reset(): void {
        if(this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
        this.update();
    }
}
