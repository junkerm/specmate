import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';

import { IContainer } from '../../../model/IContainer';
import { CEGModel } from '../../../model/CEGModel';
import { CEGNode } from '../../../model/CEGNode';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { CEGConnection } from '../../../model/CEGConnection';

import { ITool } from './tools/ITool';
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

    @Input()
    model: CEGModel;

    @Input()
    contents: IContainer[];

    editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    causeNodeType = CEGCauseNode;
    nodeType = CEGNode;
    effectNodeType = CEGEffectNode;
    connectionType = CEGConnection;

    private tools: ITool[];
    private activeTool: ITool;

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

    private activate(tool: ITool): void {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    }

    private isActive(tool: ITool): boolean {
        return this.activeTool === tool;
    }

    private get selectedNodes(): (CEGNode | CEGConnection)[] {
        if (this.activeTool) {
            return this.activeTool.selectedElements;
        }
        return [];
    }

    private isSelected(element: CEGNode | CEGConnection) {
        return this.selectedNodes.indexOf(element) >= 0;
    }

    private select(element: CEGNode | CEGConnection): void {
        if (this.activeTool) {
            this.activeTool.select(element);
        }
        this.navigateToSelectedElement();
    }

    private click(evt: MouseEvent): void {
        if (this.activeTool) {
            this.activeTool.click(evt);
        }
        this.navigateToSelectedElement();
    }

    private navigateToSelectedElement(): void {
        if (this.selectedNodes && this.selectedNodes.length > 0) {
            let selectedNode: CEGNode | CEGConnection = this.selectedNodes[this.selectedNodes.length - 1];
            this.router.navigate([{ outlets: { 'ceg-node-details': [selectedNode.url, 'ceg-node-details'] } }], { relativeTo: this.route });
        }
    }
}
