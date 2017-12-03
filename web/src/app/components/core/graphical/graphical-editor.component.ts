import { Id } from '../../../util/Id';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { Type } from '../../../util/Type';
import { ChangeDetectionStrategy, ViewChildren, QueryList, ViewChild, SimpleChange, Component, Input, ChangeDetectorRef } from '@angular/core';

import { Config } from '../../../config/config';

import { IContainer } from '../../../model/IContainer';
import { CEGModel } from '../../../model/CEGModel';

import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { CEGGraphicalConnection } from "./elements/ceg/ceg-graphical-connection.component";
import { ElementProvider } from "./providers/element-provider";
import { NameProvider } from "./providers/name-provider";
import { Process } from "../../../model/Process";
import { ToolProvider } from './providers/tool-provider';
import { EditorToolsService } from '../../../services/editor/editor-tools.service';
import { SelectedElementService } from '../../../services/editor/selected-element.service';
import { ValidationService } from '../../../services/validation/validation.service';
import { ISpecmatePositionableModelObject } from '../../../model/ISpecmatePositionableModelObject';
import { ViewControllerService } from '../../../services/view/view-controller.service';


@Component({
    moduleId: module.id,
    selector: 'graphical-editor',
    templateUrl: 'graphical-editor.component.html',
    styleUrls: ['graphical-editor.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class GraphicalEditor {

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, protected editorToolsService: EditorToolsService, private selectedElementService: SelectedElementService, private validationService: ValidationService, private viewController: ViewControllerService) { }

    private nameProvider: NameProvider;
    private toolProvider: ToolProvider;
    private elementProvider: ElementProvider;
    
    public isGridShown: boolean = true;

    protected zoom: number = 1;

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
    
    public get isValid(): boolean {
        return this.validationService.isValid(this.model) && this.validationService.allValid(this.contents);
    }

    public maximize(): void {
        this.viewController.maximizeEditor();
    }

    public unmaximize(): void {
        this.viewController.unmaximizeEditor();
    }

    public get isMaximized(): boolean {
        return this.viewController.isEditorMaximized;
    }

    public zoomIn(): void {
        if(this.canZoomIn) {
            this.zoom += Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    }

    public zoomOut(): void {
        if(this.canZoomOut) {
            this.zoom -= Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    }

    public resetZoom(): void {
        this.zoom = 1;
    }

    public get canZoomIn(): boolean {
        return this.zoom < Config.GRAPHICAL_EDITOR_ZOOM_MAX;
    }

    public get canZoomOut(): boolean {
        return this.zoom > Config.GRAPHICAL_EDITOR_ZOOM_STEP * 2;
    }

    public showGrid(): void {
        this.isGridShown = true;
    }

    public hideGrid(): void {
        this.isGridShown = false;
    }

    public get editorDimensions(): {width: number, height: number} {
        let dynamicWidth: number = Config.GRAPHICAL_EDITOR_WIDTH;
        let dynamicHeight: number = Config.GRAPHICAL_EDITOR_HEIGHT;
        
        let nodes: ISpecmatePositionableModelObject[] = this.contents.filter((element: IContainer) => {
            return (element as ISpecmatePositionableModelObject).x !== undefined && (element as ISpecmatePositionableModelObject).y !== undefined ;
        }) as ISpecmatePositionableModelObject[];

        for(let i = 0; i < nodes.length; i++) {
            let nodeX: number = nodes[i].x + (Config.GRAPHICAL_EDITOR_PADDING_HORIZONTAL);
            if(dynamicWidth < nodeX) {
                dynamicWidth = nodeX;
            }

            let nodeY: number = nodes[i].y + (Config.GRAPHICAL_EDITOR_PADDING_VERTICAL);
            if(dynamicHeight < nodeY) {
                dynamicHeight = nodeY;
            }
        }
        return {width: dynamicWidth, height: dynamicHeight};
    }

    public get gridSize(): number {
        return Config.GRAPHICAL_EDITOR_GRID_SPACE;
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
}
