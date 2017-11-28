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
import { EditorToolsService } from '../../../services/editor/editor-tools.service';


@Component({
    moduleId: module.id,
    selector: 'graphical-editor',
    templateUrl: 'graphical-editor.component.html',
    styleUrls: ['graphical-editor.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class GraphicalEditor extends GraphicalEditorBase {

    private elementProvider: ElementProvider;
    private toolProvider: ToolProvider;
    private nameProvider: NameProvider;

    constructor(private dataService: SpecmateDataService, private changeDetectorRef: ChangeDetectorRef, private modal: ConfirmationModal, protected editorToolsService: EditorToolsService) {
        super(editorToolsService);
    }

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
        //this.editorToolsService.activateDefaultTool(contents);
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

    public get cursor(): string {
        return this.editorToolsService.cursor;
    }

    public get isCEGModel(): boolean {
        return Type.is(this.model, CEGModel);
    }

    public get isProcessModel(): boolean {
        return Type.is(this.model, Process);
    }

    public get isValid(): boolean {
        if (!this.nodeDetails) {
            return true;
        }
        return !this.nodeDetails.some((details: GraphicalElementDetails) => !details.isValid);
    }

    private isValidElement(element: IContainer): boolean {
        if(!this.nodeDetails) {
            return true;
        }
        let nodeDetail: GraphicalElementDetails = this.nodeDetails.find((details: GraphicalElementDetails) => details.element === element);
        if (!nodeDetail) {
            return true;
        }
        return nodeDetail.isValid;
    }

    private get selectedNodes(): IContainer[] {
        if (this.editorToolsService.activeTool) {
            return this.editorToolsService.activeTool.selectedElements;
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
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.select(element).then(() => {
                if(this.editorToolsService.activeTool.done) {
                    this.editorToolsService.activateDefaultTool();
                }
            });
        }
    }

    private click(evt: MouseEvent): void {
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.click(evt, this.zoom).then(() => {
                if(this.editorToolsService.activeTool.done) {
                    this.editorToolsService.activateDefaultTool();
                }
            });
        }
    }
}
