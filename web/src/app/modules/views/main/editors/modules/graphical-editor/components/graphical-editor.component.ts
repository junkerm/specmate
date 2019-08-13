import { ChangeDetectionStrategy, Component, ElementRef, HostListener, Input, Renderer, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Config } from '../../../../../../../config/config';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { Process } from '../../../../../../../model/Process';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { SelectionRect } from '../../../../../side/modules/selected-element/util/selection-rect';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { EditorToolsService } from '../../tool-pallette/services/editor-tools.service';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { IDoubleClickTool } from '../../tool-pallette/tools/IDoubleClickTool';
import { IDragAndDropTool } from '../../tool-pallette/tools/IDragAndDropTool';
import { IKeyboardTool } from '../../tool-pallette/tools/IKeyboardTool';
import { ToolBase } from '../../tool-pallette/tools/tool-base';
import { ElementProvider } from '../providers/properties/element-provider';
import { NameProvider } from '../providers/properties/name-provider';
import { ToolProvider } from '../providers/properties/tool-provider';
import { Area, Line, Point, Square } from '../util/area';

import { mxgraph } from 'mxgraph'; // Typings only - no code!

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
    mxBasePath: 'mxgraph'
});



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

    private toolUseLock = false;

    private visibleArea = new Square(0, 0, 500, 500);
    private _focus = false;
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
        protected editorToolsService: EditorToolsService,
        private selectedElementService: SelectedElementService,
        private validationService: ValidationService,
        private translate: TranslateService,
        public multiselectionService: MultiselectionService,
        private clipboardService: ClipboardService,
        private renderer: Renderer) { }


    private graph: mxgraph.mxGraph;
    @ViewChild('mxGraphContainer')
    public set graphContainer(element: ElementRef) {
        console.log(element);
        if (element === undefined) {
            return;
        }
        this.graph = new mx.mxGraph(element.nativeElement);
        const parent = this.graph.getDefaultParent();
        this.graph.setGridEnabled(true);
        const vertexStyle = this.graph.getStylesheet().getDefaultVertexStyle();
        vertexStyle[mx.mxConstants.STYLE_ROUNDED] = false;

        const style = {};
        style[mx.mxConstants.STYLE_STROKECOLOR] = '#000000';
        style[mx.mxConstants.STYLE_SHAPE] = mx.mxConstants.SHAPE_HEXAGON;
        this.graph.getStylesheet().putDefaultVertexStyle(style);

        const edgeStyle = this.graph.getStylesheet().getDefaultEdgeStyle();
        edgeStyle[mx.mxConstants.EDGESTYLE_ELBOW] = false;
        edgeStyle[mx.mxConstants.EDGESTYLE_ENTITY_RELATION] = false;
        edgeStyle[mx.mxConstants.EDGESTYLE_LOOP] = false;
        edgeStyle[mx.mxConstants.EDGESTYLE_ORTHOGONAL] = false;
        edgeStyle[mx.mxConstants.EDGESTYLE_SEGMENT] = false;
        edgeStyle[mx.mxConstants.EDGESTYLE_SIDETOSIDE] = true;
        edgeStyle[mx.mxConstants.EDGESTYLE_TOPTOBOTTOM] = false;
        edgeStyle[mx.mxConstants.STYLE_DASHED] = true;
        edgeStyle[mx.mxConstants.STYLE_STROKECOLOR] = '#ff0000';

        this.graph.getModel().beginUpdate();
        try {
            const vertex1 = this.graph.insertVertex(parent, '1', 'Vertex 1', 0, 0, 200, 80);
            const vertex2 = this.graph.insertVertex(parent, '2', 'Vertex 2', 0, 0, 200, 80);

            const elem = this.graph.insertEdge(parent, '1-2', 'Edge 1', vertex1, vertex2);

        } finally {
            this.graph.getModel().endUpdate();
            new mx.mxHierarchicalLayout(this.graph).execute(this.graph.getDefaultParent());
        }
    }


    public get model(): IContainer {
        return this._model;
    }

    public get isRectShowing(): boolean {
        return this.multiselectionService.selectionRect.isRectDrawing;
    }

    public get rect(): SelectionRect {
        return this.multiselectionService.selectionRect;
    }

    @Input()
    public set model(model: IContainer) {
        this.toolProvider = new ToolProvider(
            model, this.dataService, this.selectedElementService, this.translate, this.multiselectionService, this.clipboardService);
        this.nameProvider = new NameProvider(model, this.translate);
        this._model = model;
    }

    @Input()
    public set contents(contents: IContainer[]) {
        this._contents = contents;
        this.elementProvider = new ElementProvider(this.model, this._contents);
    }

    private set focus(newFocus: boolean) {
        if (newFocus === this._focus) {
            return;
        }
        this._focus = newFocus;
        // Update Native Element
        if (this._editor) {
            if (newFocus) {
                this.renderer.invokeElementMethod(this._editor.nativeElement, 'focus');
            } else {
                this.renderer.invokeElementMethod(this._editor.nativeElement, 'blur');
            }
        }
    }

    private get focus(): boolean {
        return this._focus;
    }

    public get contents(): IContainer[] {
        return this._contents;
    }

    public get isValid(): boolean {
        return this.validationService.isValid(this.model);
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

    public get editorDimensions(): { width: number, height: number } {
        let dynamicWidth: number = Config.GRAPHICAL_EDITOR_WIDTH;
        let dynamicHeight: number = Config.GRAPHICAL_EDITOR_HEIGHT;

        let nodes: ISpecmatePositionableModelObject[] = this.contents.filter((element: IContainer) => {
            return (element as ISpecmatePositionableModelObject).x !== undefined &&
                (element as ISpecmatePositionableModelObject).y !== undefined;
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
        return { width: dynamicWidth, height: dynamicHeight };
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

    private isVisibleConnection(connection: IContainer): boolean {
        let nodes = this.nodes;
        let start = <any>nodes.find(node => node.url === (<any>connection).source.url);
        let end = <any>nodes.find(node => node.url === (<any>connection).target.url);
        if (!start || !end) {
            return false;
        }
        return Area.checkLineInArea(this.visibleArea, new Line(start.x, start.y, end.x, end.y));
    }

    private isSelectedComparator(a: IContainer, b: IContainer): number {
        if (this.selectedElementService.isSelected(a)) {
            return 1;
        }
        if (this.selectedElementService.isSelected(b)) {
            return -1;
        }
        return 0;
    }

    /**
     * Returns all connections visible to the viewer.
     * Since they are drawn in order and we want the elements that the user is currently moving to always be fully visible
     * we sort the list so that all selected elements are drawn last (i.e. on top of the others).
     */
    public get visibleConnections(): IContainer[] {
        let selectedConnections = this.connections.filter(connection => this.isVisibleConnection(connection));
        // Sort the elements
        selectedConnections.sort((a: IContainer, b: IContainer) => this.isSelectedComparator(a, b));
        return selectedConnections;
    }

    /**
     * Returns all nodes visible to the viewer.
     * Since they are drawn in order and we want the elements that the user is currently moving to always be fully visible
     * we sort the list so that all selected elements are drawn last (i.e. on top of the others).
     */
    public get visibleNodes(): IContainer[] {
        let visibleNodes = this.nodes.filter(node => Area.checkPointInArea(this.visibleArea, new Point((<any>node).x, (<any>node).y)));
        // Sort the elements
        visibleNodes.sort((a: IContainer, b: IContainer) => this.isSelectedComparator(a, b));
        return this.nodes;
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

    public onScroll(event: UIEvent): void {
        let target = <any>event.target;
        this.setVisibleArea(target);
    }

    private setVisibleArea(target: any) {
        let eWidth = this.editorDimensions.width;
        let eHeight = this.editorDimensions.height;

        let xMin = eWidth * (target.scrollLeft / target.scrollWidth);
        let xMax = xMin + (target.clientWidth / this.zoom);
        let yMin = eHeight * (target.scrollTop / target.scrollHeight);
        let yMax = yMin + (target.clientHeight / this.zoom);

        xMin -= Config.GRAPHICAL_EDITOR_VISIBILITY_HORIZONTAL;
        yMin -= Config.GRAPHICAL_EDITOR_VISIBILITY_VERTICAL;
        xMax += Config.GRAPHICAL_EDITOR_VISIBILITY_HORIZONTAL;
        yMax += Config.GRAPHICAL_EDITOR_VISIBILITY_VERTICAL;

        this.visibleArea = new Square(xMin, yMin, xMax, yMax);
    }

    private checkAndReset(evt: MouseEvent | KeyboardEvent) {
        if (this.editorToolsService.activeTool.done) {
            this.toolUseLock = evt.shiftKey;
            if (!this.toolUseLock && !this.editorToolsService.activeTool.sticky) {
                this.editorToolsService.activateDefaultTool();
            }
        }
        if (!this.selectedElementService.hasSelection) {
            this.selectedElementService.select(this.model);
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
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.click(evt, this.zoom).then(() => {
                if (!this.selectedElementService.hasSelection) {
                    // The selection tool has finished and nothing was selected -> Default to model
                    this.selectedElementService.selectedElement = this.model;
                }
                this.checkAndReset(evt);
            });
        }
    }

    private isDoubleClickTool(tool: ToolBase): boolean {
        return (tool as IDoubleClickTool).dblClick !== undefined;
    }

    private dblclick(element: IContainer, evt: MouseEvent) {
        if (this.editorToolsService.activeTool && this.isDoubleClickTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IDoubleClickTool).dblClick(element, evt).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private isDragDropTool(tool: ToolBase): boolean {
        let iTool = tool as IDragAndDropTool;
        let down = iTool.mouseDown !== undefined;
        let up = iTool.mouseUp !== undefined;
        let move = iTool.mouseDrag !== undefined;
        return down && up && move;
    }

    private mousedown(evt: MouseEvent): void {
        evt.preventDefault();
        evt.stopPropagation();

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IDragAndDropTool).mouseDown(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private mousemove(evt: MouseEvent): void {
        // We only care about mousemovement when a button is pressed (i.e. drag & drop)
        if (evt.buttons <= 0) {
            return;
        }

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IDragAndDropTool).mouseDrag(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private mouseup(evt: MouseEvent): void {
        this.focus = true;
        evt.preventDefault();
        evt.stopPropagation();

        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IDragAndDropTool).mouseUp(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private mouseleave(evt: MouseEvent): void {
        if (this.editorToolsService.activeTool && this.isDragDropTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IDragAndDropTool).mouseUp(evt, this.zoom).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    private isKeyboardShortcutTool(tool: ToolBase): boolean {
        return (tool as IKeyboardTool).keydown !== undefined;
    }

    @HostListener('window:keydown', ['$event'])
    hostKeyEvent(evt: KeyboardEvent) {
        if (!this.focus) {
            return;
        }
        if (this.editorToolsService.activeTool && this.isKeyboardShortcutTool(this.editorToolsService.activeTool)) {
            (this.editorToolsService.activeTool as IKeyboardTool).keydown(evt).then(() => {
                this.checkAndReset(evt);
            });
        }
    }

    @HostListener('window:mousedown')
    hostMouseDown() {
        this.focus = false;
    }
}
