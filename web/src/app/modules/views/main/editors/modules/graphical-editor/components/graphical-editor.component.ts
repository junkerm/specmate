import { Component, ChangeDetectionStrategy, Input, HostListener, ViewChild, ElementRef } from '@angular/core';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../forms/modules/validation/services/validation.service';
import { ViewControllerService } from '../../../../../controller/modules/view-controller/services/view-controller.service';
import { NameProvider } from '../providers/properties/name-provider';
import { ToolProvider } from '../providers/properties/tool-provider';
import { ElementProvider } from '../providers/properties/element-provider';
import { IContainer } from '../../../../../../../model/IContainer';
import { Config } from '../../../../../../../config/config';
import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { Type } from '../../../../../../../util/type';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { Process } from '../../../../../../../model/Process';
import { EditorToolsService } from '../../tool-pallette/services/editor-tools.service';
import { TranslateService } from '@ngx-translate/core';
import { Area, Square, Point, Line } from '../util/area';
import { ToolBase } from '../../tool-pallette/tools/tool-base';
import { DragAndDropToolInterface } from '../../tool-pallette/tools/drag-and-drop-tool-interface';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { KeyboardToolInterface } from '../../tool-pallette/tools/keyboard-tool-interface';
import { DoubleClickToolInterface } from '../../tool-pallette/tools/doubleclick-tool-interface';

@Component({
    moduleId: module.id.toString(),
    selector: 'graphical-editor',
    templateUrl: 'graphical-editor.component.html',
    styleUrls: ['graphical-editor.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class GraphicalEditor {

    private nameProvider: NameProvider;
    private toolProvider: ToolProvider;
    private elementProvider: ElementProvider;

    public isGridShown = true;

    protected zoom = 1;

    private _model: IContainer;

    private _contents: IContainer[];

    private _editor: ElementRef;
    @ViewChild('editorElement')
    public set editorElement(editor: ElementRef) {
        if (!editor) {
            return;
        }
        this.setVisibleArea(editor.nativeElement);
        this._editor = editor;
    }
    constructor(
        private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        protected editorToolsService: EditorToolsService,
        private selectedElementService: SelectedElementService,
        private validationService: ValidationService,
        private viewController: ViewControllerService,
        private translate: TranslateService,
        public rectService: MultiselectionService) { }

    public get model(): IContainer {
        return this._model;
    }

    public get showRect(): boolean {
        return this.rectService.selectionRect.drawRect;
    }

    public get rect(): any {
        return this.rectService.selectionRect;
    }

    @Input()
    public set model(model: IContainer) {
        this.toolProvider = new ToolProvider(model, this.dataService, this.selectedElementService, this.translate, this.rectService);
        this.nameProvider = new NameProvider(model, this.translate);
        this._model = model;
    }

    @Input()
    public set contents(contents: IContainer[]) {
        this._contents = contents;
        this.elementProvider = new ElementProvider(this.model, this._contents);
    }

    public get contents(): IContainer[] {
        return this._contents;
    }

    public get isValid(): boolean {
        return this.validationService.isValid(this.model, this.contents);
    }

    public zoomIn(): void {
        if (this.canZoomIn) {
            this.zoom += Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    }

    public zoomOut(): void {
        if (this.canZoomOut) {
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
            return (element as ISpecmatePositionableModelObject).x !== undefined &&
                (element as ISpecmatePositionableModelObject).y !== undefined ;
        }) as ISpecmatePositionableModelObject[];

        for (let i = 0; i < nodes.length; i++) {
            let nodeX: number = nodes[i].x + (Config.GRAPHICAL_EDITOR_PADDING_HORIZONTAL);
            if (dynamicWidth < nodeX) {
                dynamicWidth = nodeX;
            }

            let nodeY: number = nodes[i].y + (Config.GRAPHICAL_EDITOR_PADDING_VERTICAL);
            if (dynamicHeight < nodeY) {
                dynamicHeight = nodeY;
            }
        }
        return {width: dynamicWidth, height: dynamicHeight};
    }

    public get gridSize(): number {
        return Config.GRAPHICAL_EDITOR_GRID_SPACE;
    }

    public get connections(): IContainer[] {
        if (!this.elementProvider) {
            return [];
        }
        return this.elementProvider.connections;
    }

    public get nodes(): IContainer[] {
        if (!this.elementProvider) {
            return [];
        }
        return this.elementProvider.nodes;
    }

    public get visibleConnections(): IContainer[] {
        return this.connections.filter( connection => {
            let nodes = this.nodes;
            let start = <any>nodes.find( node => node.url === (<any>connection).source.url);
            let end = <any>nodes.find( node => node.url === (<any>connection).target.url);
            if (!start || !end) {
                return false;
            }
            return Area.checkLineInArea(this.visibleArea, new Line(start.x, start.y, end.x, end.y));
        });
    }

    public get visibleNodes(): IContainer[] {
        return this.nodes.filter( node => Area.checkPointInArea(this.visibleArea, new Point( (<any>node).x, (<any>node).y)));
    }

    public get name(): string {
        if (!this.nameProvider) {
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

    private visibleArea = new Square(0, 0, 500, 500);

    public onScroll(event: UIEvent): void {
        let target = <any> event.target;
        this.setVisibleArea(target);
    }

    private setVisibleArea(target: any) {
        let eWidth  = this.editorDimensions.width;
        let eHeight = this.editorDimensions.height;

        let xMin = eWidth  * (target.scrollLeft / target.scrollWidth);
        let xMax = xMin    + (target.clientWidth / this.zoom);
        let yMin = eHeight * (target.scrollTop / target.scrollHeight);
        let yMax = yMin    + (target.clientHeight / this.zoom);

        this.visibleArea = new Square(xMin - 100, yMin - 100, xMax + 100, yMax + 100);
    }

    private toolUseLock = false;
    private checkAndReset(evt: MouseEvent) {
        if (this.editorToolsService.activeTool.done) {
            this.toolUseLock = evt.shiftKey;
            if (!this.toolUseLock) {
                this.editorToolsService.activateDefaultTool();
            }
        }
    }

    private select(element: IContainer, event: MouseEvent): void {
        this.focus = true;
        event.preventDefault();
        event.stopPropagation();
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.select(element, event).then(() => {
                this.checkAndReset(event);
            });
        }
    }

    private click(evt: MouseEvent): void {
        this.focus = true;

        if (!this.selectedElementService.hasSelection) {
            // The selection tool has finished and nothing was selected -> Default to model
            this.selectedElementService.selectedElement = this.model;
        }
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.click(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private isDoubleClickTool(tool: ToolBase): boolean {
        return (tool as DoubleClickToolInterface).dblClick !== undefined;
    }

    private dblclick(element: IContainer, evt: MouseEvent) {
        if (this.editorToolsService.activeTool && this.isDoubleClickTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as DoubleClickToolInterface).dblClick(element, evt).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private isDragDropTool(tool: ToolBase): boolean {
        let iTool = tool as DragAndDropToolInterface;
        let down = iTool.mouseDown !== undefined;
        let up   = iTool.mouseUp !== undefined;
        let move = iTool.mouseDrag !== undefined;
        return down && up && move;
    }

    private _mousePressed = false;
    private mousedown(evt: MouseEvent): void {
        evt.preventDefault();
        evt.stopPropagation();
        this._mousePressed = true;

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as DragAndDropToolInterface).mouseDown(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private mousemove(evt: MouseEvent): void {
        if (!this._mousePressed) {
            return;
        }

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as DragAndDropToolInterface).mouseDrag(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private mouseup(evt: MouseEvent): void {
        evt.preventDefault();
        if (!this._mousePressed) {
            return;
        }
        evt.stopPropagation();
        this._mousePressed = false;

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as DragAndDropToolInterface).mouseUp(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private isKeyboardShortcutTool(tool: ToolBase): boolean {
        return (tool as KeyboardToolInterface).keydown !== undefined;
    }

    private focus = false;
    @HostListener('window:keydown', ['$event'])
    keyEvent(evt: KeyboardEvent) {
        if (!this.focus) {
            return;
        }
        if (this.editorToolsService.activeTool && this.isKeyboardShortcutTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as KeyboardToolInterface).keydown(evt);
        }
    }

    @HostListener('window:keyup', ['$event'])
    keyUpEvent(evt: KeyboardEvent) {
        if (this.toolUseLock) {
            if (!evt.shiftKey) {
                this.toolUseLock = false;
                this.editorToolsService.activateDefaultTool();
            }
        }
    }

    @HostListener('window:mousedown')
    mouseDown() {
        this.focus = false;
    }
}
