import { Id } from '../../../util/Id';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { Type } from '../../../util/Type';
import { GraphicalElementDetails } from './graphical-element-details.component';
import { ChangeDetectionStrategy, ViewChildren, QueryList, ViewChild, SimpleChange, Component, Input, ChangeDetectorRef } from '@angular/core';

import { Config } from '../../../config/config';

import { IContainer } from '../../../model/IContainer';
import { CEGModel } from '../../../model/CEGModel';

import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { GraphicalEditorBase } from "../../core/graphical/graphical-editor-base";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { CEGGraphicalConnection } from "./elements/ceg/ceg-graphical-connection.component";
import { ITool } from "./tools/i-tool";
import { ElementProvider } from "./providers/element-provider";
import { NameProvider } from "./providers/name-provider";
import { Process } from "../../../model/Process";
import { ToolProvider } from './providers/tool-provider';


@Component({
    moduleId: module.id,
    selector: 'graphical-editor',
    templateUrl: 'graphical-editor.component.html',
    styleUrls: ['graphical-editor.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class GraphicalEditor extends GraphicalEditorBase {

    private activeTool: ITool;

    private elementProvider: ElementProvider;
    private toolProvider: ToolProvider;
    private nameProvider: NameProvider;

    @ViewChildren(GraphicalElementDetails)
    private nodeDetails: QueryList<GraphicalElementDetails>;

    private _model: IContainer;

    public get model(): IContainer {
        return this._model;
    }

    @Input()
    public set model(model: IContainer) {
        this.toolProvider = new ToolProvider(model, this.dataService);
        this.nameProvider = new NameProvider(model);
        this._model = model;
    }

    private _contents: IContainer[];
    @Input()
    public set contents(contents: IContainer[]) {
        this._contents = contents;
        this.elementProvider = new ElementProvider(this.model, this._contents);
        this.activateDefaultTool();
    }

    public get contents(): IContainer[] {
        return this._contents;
    }

    public get connections(): IContainer[] {
        if(!this.elementProvider) {
            return [];
        }
        return this.elementProvider.connections;
    }

    public get nodes(): IContainer[] {
        if(!this.elementProvider) {
            return [];
        }
        return this.elementProvider.nodes;
    }

    public get name(): string {
        if(!this.nameProvider) {
            return '';
        }
        return this.nameProvider.name;
    }

    public get isCEGModel(): boolean {
        return Type.is(this.model, CEGModel);
    }

    public get isProcessModel(): boolean {
        return Type.is(this.model, Process);
    }

    public get tools(): ITool[] {
        if(!this.toolProvider) {
            return [];
        }
        return this.toolProvider.tools;
    }

    constructor(private dataService: SpecmateDataService, private changeDetectorRef: ChangeDetectorRef, private modal: ConfirmationModal) {
        super();
    }

    private get cursor(): string {
        if(this.activeTool) {
            return this.activeTool.cursor;
        }
        return 'auto';
    }

    public get isValid(): boolean {
        if (!this.nodeDetails) {
            return true;
        }
        return !this.nodeDetails.some((details: GraphicalElementDetails) => !details.isValid);
    }

    private isValidElement(element: IContainer): boolean {
        if(!this.nodeDetails){
            return true;
        }
        let nodeDetail: GraphicalElementDetails = this.nodeDetails.find((details: GraphicalElementDetails) => details.element === element);
        if (!nodeDetail) {
            return true;
        }
        return nodeDetail.isValid;
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

    private select(element: IContainer, event: MouseEvent): void {
        event.preventDefault();
        event.stopPropagation();
        if (this.activeTool) {
            this.activeTool.select(element).then(() => {
                if(this.activeTool.done) {
                    this.activateDefaultTool();
                }
            });
        }
    }

    private click(evt: MouseEvent): void {
        if (this.activeTool) {
            this.activeTool.click(evt).then(() => {
                if(this.activeTool.done) {
                    this.activateDefaultTool();
                }
            });
        }
    }

    public reset(): void {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
    }

    private activateDefaultTool(): void {
        if(!this.tools) {
            return;
        }
        if(this.contents && this.contents.length > 0) {
            this.activate(this.tools[0]);
        }
        else {
            this.activate(this.tools[1]);
        }
    }
    
    private delete(): void {
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(() => this.removeAllElements())
            .catch(() => {});
    }

    private removeAllElements(): void {
        let compoundId: string = Id.uuid;
        for (let i = this.elementProvider.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.elementProvider.connections[i].url, true, compoundId);
        }
        for (let i = this.elementProvider.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.elementProvider.nodes[i].url, true, compoundId);
        }
    }
}
