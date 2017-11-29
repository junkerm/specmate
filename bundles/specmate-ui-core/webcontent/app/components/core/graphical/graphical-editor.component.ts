import { Id } from '../../../util/Id';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { Type } from '../../../util/Type';
import { ChangeDetectionStrategy, ViewChildren, QueryList, ViewChild, SimpleChange, Component, Input, ChangeDetectorRef } from '@angular/core';

import { Config } from '../../../config/config';

import { IContainer } from '../../../model/IContainer';
import { CEGModel } from '../../../model/CEGModel';

import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { GraphicalEditorBase } from "../../core/graphical/graphical-editor-base";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { CEGGraphicalConnection } from "./elements/ceg/ceg-graphical-connection.component";
import { ElementProvider } from "./providers/element-provider";
import { NameProvider } from "./providers/name-provider";
import { Process } from "../../../model/Process";
import { ToolProvider } from './providers/tool-provider';
import { EditorToolsService } from '../../../services/editor/editor-tools.service';
import { SelectedElementService } from '../../../services/editor/selected-element.service';


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

    constructor(private dataService: SpecmateDataService, private changeDetectorRef: ChangeDetectorRef, private modal: ConfirmationModal, protected editorToolsService: EditorToolsService, private selectedElementService: SelectedElementService) {
        super(editorToolsService);
    }

    private _model: IContainer;

    public get model(): IContainer {
        return this._model;
    }

    @Input()
    public set model(model: IContainer) {
        this.toolProvider = new ToolProvider(model, this.dataService, this.selectedElementService);
        this.nameProvider = new NameProvider(model);
        this._model = model;
    }

    private _contents: IContainer[];
    @Input()
    public set contents(contents: IContainer[]) {
        this._contents = contents;
        this.elementProvider = new ElementProvider(this.model, this._contents);
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
        this.selectedElementService.selectedElement = this.model;
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.click(evt, this.zoom).then(() => {
                if(this.editorToolsService.activeTool.done) {
                    this.editorToolsService.activateDefaultTool();
                }
            });
        }
    }

    public get isValid(): boolean {
        return true;
    }
}
