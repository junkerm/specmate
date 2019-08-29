import { ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { mxgraph } from 'mxgraph'; // Typings only - no code!
import { Config } from '../../../../../../../config/config';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { Process } from '../../../../../../../model/Process';
import { Arrays } from '../../../../../../../util/arrays';
import { Id } from '../../../../../../../util/id';
import { Type } from '../../../../../../../util/type';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { SelectionRect } from '../../../../../side/modules/selected-element/util/selection-rect';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { ConnectionToolBase } from '../../tool-pallette/tools/connection-tool-base';
import { CreateNodeToolBase } from '../../tool-pallette/tools/create-node-tool-base';
import { ToolBase } from '../../tool-pallette/tools/tool-base';
import { ConverterBase } from '../converters/converter-base';
import { NodeNameConverterProvider } from '../providers/conversion/node-name-converter-provider';
import { ElementProvider } from '../providers/properties/element-provider';
import { NameProvider } from '../providers/properties/name-provider';
import { ToolProvider } from '../providers/properties/tool-provider';


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
    private elementProvider: ElementProvider;
    private toolProvider: ToolProvider;
    private nodeNameConverter: ConverterBase<any, string>;

    public isGridShown = true;

    private _model: IContainer;
    private _contents: IContainer[];
    private readonly VALID_STYLE_NAME = 'VALID';
    private readonly INVALID_STYLE_NAME = 'INVALID';

    private preventDataUpdates = false;

    constructor(
        private dataService: SpecmateDataService,
        private selectedElementService: SelectedElementService,
        private validationService: ValidationService,
        private translate: TranslateService,
        public multiselectionService: MultiselectionService,
        private clipboardService: ClipboardService) {
        this.validationService.validationFinished.subscribe(() => {
            this.updateValidities();
        });
    }


    @ViewChild('mxGraphToolbar')
    private toolbarElem: ElementRef;

    private graph: mxgraph.mxGraph;
    private keyHandler: mxgraph.mxKeyHandler;

    @ViewChild('mxGraphContainer')
    public set graphContainer(element: ElementRef) {

        mx.mxConnectionHandler.prototype.connectImage = new mx.mxImage('assets/img/editor-tools/connector.png', 16, 16);
        mx.mxEvent.disableContextMenu(document.body);
        mx.mxGraphHandler.prototype['guidesEnabled'] = true;

        if (element === undefined) {
            return;
        }

        this.graph = new mx.mxGraph(element.nativeElement);
        this.graph.setGridEnabled(true);
        this.graph.setConnectable(true);
        this.graph.setMultigraph(false);
        const rubberBand = new mx.mxRubberband(this.graph);
        rubberBand.reset();

        this.createStyles();


        this.graph.setTooltips(true);

        // Configures automatic expand on mouseover
        this.graph.popupMenuHandler['autoExpand'] = true;

        this.graph.popupMenuHandler['factoryMethod'] = function (menu: mxgraph.mxPopupMenu, cell: mxgraph.mxCell, evt: any) {
            menu.createSubmenu(cell.getParent());
            // tslint:disable-next-line:max-line-length
            menu.addItem('Del 1', null, (args: any) => console.log(args), this.graph.getDefaultParent(), null, true, false);
            // tslint:disable-next-line:max-line-length
            menu.addItem('Del 2', null, (args: any) => console.log(args), this.graph.getDefaultParent(), null, true, false);
            // tslint:disable-next-line:max-line-length
            menu.addItem('Del 3', null, (args: any) => console.log(args), this.graph.getDefaultParent(), null, true, false);
            // tslint:disable-next-line:max-line-length
            menu.addItem('Del 4', null, (args: any) => console.log(args), this.graph.getDefaultParent(), null, true, false);
        };

        this.graph.getModel().addListener(mx.mxEvent.CHANGE, (sender: mxgraph.mxEventSource, evt: mxgraph.mxEventObject) => {
            const edit = evt.getProperty('edit') as mxgraph.mxUndoableEdit;
            const changes = edit.changes;
            for (const change of changes) {
                try {
                    this.translateArbitraryChange(change);
                } catch (e) {
                    this.preventDataUpdates = true;
                    edit.undo();
                    this.preventDataUpdates = false;
                }
            }
        });

        this.graph.getSelectionModel().addListener(mx.mxEvent.CHANGE, async (args: any) => {
            if (this.graph.getSelectionCount() === 1) {
                const selectedElement = await this.dataService.readElement(this.graph.getSelectionModel().cells[0].getId(), true);
                this.selectedElementService.select(selectedElement);
            } else {
                this.selectedElementService.deselect();
            }
        });

        this.keyHandler = new mx.mxKeyHandler(this.graph);
        this.keyHandler.bindKey(46, (evt: KeyboardEvent) => {
            if (this.graph.isEnabled()) {
                const selectedCells = this.graph.getSelectionCells().filter(cell => !cell.edge);
                this.graph.removeCells(selectedCells, true);
            }
        });

        this.init();
    }

    private createStyles() {

        mx.mxConstants.HANDLE_FILLCOLOR = '#99ccff';
        mx.mxConstants.HANDLE_STROKECOLOR = '#0088cf';
        mx.mxConstants.VERTEX_SELECTION_COLOR = '#00a8ff';
        mx.mxConstants.EDGE_SELECTION_COLOR = '#00a8ff';

        const stylesheet = this.graph.getStylesheet();
        const validStyle: {
            [key: string]: string;
        } = {};
        validStyle[mx.mxConstants.STYLE_SHAPE] = mx.mxConstants.SHAPE_RECTANGLE;
        validStyle[mx.mxConstants.STYLE_OPACITY] = '75';
        validStyle[mx.mxConstants.STYLE_FILLCOLOR] = '#c3d9ff';
        validStyle[mx.mxConstants.STYLE_STROKE_OPACITY] = '100';
        validStyle[mx.mxConstants.STYLE_STROKECOLOR] = '#c3d9ff';
        stylesheet.putCellStyle(this.VALID_STYLE_NAME, validStyle);
        const invalidStyle: {
            [key: string]: string;
        } = {};
        invalidStyle[mx.mxConstants.STYLE_SHAPE] = mx.mxConstants.SHAPE_RECTANGLE;
        invalidStyle[mx.mxConstants.STYLE_OPACITY] = '75';
        invalidStyle[mx.mxConstants.STYLE_FILLCOLOR] = '#ffc3d9';
        invalidStyle[mx.mxConstants.STYLE_STROKE_OPACITY] = '100';
        invalidStyle[mx.mxConstants.STYLE_STROKECOLOR] = '#ffc3d9';
        stylesheet.putCellStyle(this.INVALID_STYLE_NAME, invalidStyle);
        const vertexStyle = this.graph.getStylesheet().getDefaultVertexStyle();
        vertexStyle[mx.mxConstants.STYLE_ROUNDED] = false;
        vertexStyle[mx.mxConstants.STYLE_STROKECOLOR] = '#000000';
        vertexStyle[mx.mxConstants.STYLE_ROUNDED] = true;
        vertexStyle[mx.mxConstants.STYLE_SHAPE] = mx.mxConstants.SHAPE_RECTANGLE;
        this.graph.getStylesheet().getDefaultEdgeStyle()[mx.mxConstants.STYLE_STROKECOLOR] = '#000000';
    }

    private async init(): Promise<void> {
        if (this.graph === undefined || this.model === undefined) {
            return;
        }
        this.initGraphicalModel();
        this.initToolbar();
    }

    private async initGraphicalModel(): Promise<void> {
        this._contents = await this.dataService.readContents(this.model.url, true);
        this.elementProvider = new ElementProvider(this.model, this._contents);
        this.nodeNameConverter = new NodeNameConverterProvider(this.model).nodeNameConverter;
        const parent = this.graph.getDefaultParent();
        this.preventDataUpdates = true;
        this.graph.getModel().beginUpdate();
        try {

            const vertexCache: { [url: string]: mxgraph.mxCell } = {};
            for (const node of this.elementProvider.nodes) {
                const x = node['x'];
                const y = node['y'];
                const width = Config.CEG_NODE_WIDTH;
                const height = Config.CEG_NODE_HEIGHT;
                const value = this.nodeNameConverter ? this.nodeNameConverter.convertTo(node) : node.name;
                const vertex = this.graph.insertVertex(parent, node.url, value, x, y, width, height);
                vertexCache[node.url] = vertex;
            }
            for (const connection of this.elementProvider.connections.map(element => element as IModelConnection)) {
                const sourceVertex = vertexCache[connection.source.url];
                const targetVertex = vertexCache[connection.target.url];
                this.graph.insertEdge(parent, connection.url, '', sourceVertex, targetVertex);
            }

        } finally {
            this.graph.getModel().endUpdate();
            this.preventDataUpdates = false;
        }
    }

    private async initToolbar(): Promise<void> {
        const toolbar = new mx.mxToolbar(this.toolbarElem.nativeElement);
        this.graph.setDropEnabled(true);
        const width = Config.CEG_NODE_WIDTH;
        const height = Config.CEG_NODE_HEIGHT;

        const tools = this.toolProvider.tools;
        for (const tool of tools.filter(t => t.isVertexTool)) {
            if (tool.style !== undefined) {
                const vertex = new mx.mxCell(null, new mx.mxGeometry(0, 0, width, height), tool.style);
                vertex.setVertex(true);
                // this.addToolbarItem(this.graph, toolbar, vertex,
                //     '/assets/img/editor-tools/' + tool.icon + '.png', this.translate.instant('addNode'));
            }
        }

        for (const tool of tools.filter(t => t.isVertexTool)) {
            const vertex = new mx.mxCell(null, new mx.mxGeometry(0, 0, width, height), tool.style);
            vertex.setVertex(true);
            const onDrop = (graph: mxgraph.mxGraph, evt: MouseEvent, cell: mxgraph.mxCell) => {
                this.graph.stopEditing(false);
                const pt = graph.getPointForEvent(evt);
                const vertexUrl = Url.build([this.model.url, Id.uuid]);
                this.graph.startEditing(evt);
                this.graph.insertVertex(
                    this.graph.getDefaultParent(),
                    vertexUrl,
                    Config.CEG_NODE_NEW_VARIABLE + ': ' + Config.CEG_NODE_NEW_CONDITION + ' style: ' + tool.style + ' tool: ' + tool.name,
                    pt.x, pt.y, Config.CEG_NODE_WIDTH, Config.CEG_NODE_HEIGHT, vertex.style);
                this.graph.stopEditing(true);
            };
            mx.mxUtils.makeDraggable(document.getElementById(tool.elementId), this.graph, onDrop);
        }
    }

    private updateValidities(): void {
        if (this.graph === undefined) {
            return;
        }
        const vertices = this.graph.getModel().getChildVertices(this.graph.getDefaultParent());
        for (const vertex of vertices) {
            this.graph.model.setStyle(vertex, this.VALID_STYLE_NAME);
        }
        const invalidNodes = this.validationService.getValidationResults(this.model);
        for (const invalidNode of invalidNodes) {
            const vertexId = invalidNode.element.url;
            const vertex = vertices.find(vertex => vertex.id === vertexId);
            this.graph.model.setStyle(vertex, this.INVALID_STYLE_NAME);
        }
    }

    private determineTool(change: (mxgraph.mxTerminalChange | mxgraph.mxChildChange)): ToolBase {
        if (change['cell'] !== undefined) {
            // Change; Do nothing
        } else if (change['child'] !== undefined) {
            const childChange = change as mxgraph.mxChildChange;
            if (childChange.child.edge) {
                // edge change
                const edgeTools = this.toolProvider.tools.filter(tool => !tool.isVertexTool);
                if (edgeTools.length > 1) {
                    return edgeTools.find(tool => tool.style === childChange.child.style);
                } else if (edgeTools.length === 1) {
                    return edgeTools[0];
                }
            } else {
                const vertexTools = this.toolProvider.tools.filter(tool => tool.isVertexTool);
                if (vertexTools.length > 1) {
                    return vertexTools.find(tool => tool.style === childChange.child.style);
                } else if (vertexTools.length === 1) {
                    return vertexTools[0];
                }
            }
        }
        throw new Error('Could not determine tool');
    }

    private async translateArbitraryChange(change: (mxgraph.mxTerminalChange | mxgraph.mxChildChange)): Promise<void> {
        if (this.preventDataUpdates) {
            return;
        }

        if (change['cell'] === undefined && change['child'] !== undefined) {
            await this.translateAdd(change as mxgraph.mxChildChange);
        } else if (change['cell'] !== undefined) {
            this.translateChange(change as mxgraph.mxTerminalChange);
        } else if (change['style']) {
            // We have a mxStyleChange
        }
    }

    private async translateAdd(change: mxgraph.mxChildChange): Promise<void> {
        if (this.contents.find(element => element.url === change.child.id) !== undefined) {
            console.log('Child already addded');
            return;
        }
        let addedElement: IContainer = undefined;
        if (change.child.edge) {
            addedElement = await this.translateEdgeAdd(change);
        } else {
            addedElement = await this.translateNodeAdd(change);
        }
        change.child.setId(addedElement.url);
    }

    private async translateEdgeAdd(change: mxgraph.mxChildChange): Promise<IModelConnection> {
        const tool = this.determineTool(change) as ConnectionToolBase<any>;
        tool.source = this.contents.find(element => element.url === change.child.source.id) as IModelNode;
        tool.target = this.contents.find(element => element.url === change.child.target.id) as IModelNode;
        const connection = await tool.perform();
        // this.contents = await this.dataService.readContents(this.model.url, true);
        change.child.id = connection.url;
        return connection;
    }

    private async translateNodeAdd(change: mxgraph.mxChildChange): Promise<IModelNode> {
        const tool = this.determineTool(change) as CreateNodeToolBase<IModelNode>;
        tool.coords = { x: change.child.geometry.x, y: change.child.geometry.y };
        const node = await tool.perform();
        // this.contents = await this.dataService.readContents(this.model.url, true);
        return node;
    }

    private translateChange(change: mxgraph.mxTerminalChange): void {
        const element = this.contents.find(element => element.url === change.cell.id);
        if (element === undefined) {
            return;
        }
        if (change.cell.edge) {
            this.translateEdgeChange(change, element as IModelConnection);
        } else {
            this.translateNodeChange(change, element as IModelNode);
        }
    }

    private translateNodeChange(change: mxgraph.mxTerminalChange, element: IModelNode) {
        if (this.nodeNameConverter) {
            const elementValues = this.nodeNameConverter.convertFrom(change.cell.value);
            for (const key in elementValues) {
                element[key] = elementValues[key];
            }
        } else {
            element['variable'] = change.cell.value;
        }
        element['x'] = change.cell.geometry.x;
        element['y'] = change.cell.geometry.y;
        this.dataService.updateElement(element, true, Id.uuid);
    }

    private async translateEdgeChange(change: mxgraph.mxTerminalChange, connection: IModelConnection): Promise<void> {
        if (change.cell.target === undefined
            || change.cell.target === null
            || change.cell.source === undefined
            || change.cell.source === null) {
            throw new Error('Source or target not defined');
        }

        if (change.previous === null) {
            return;
        }

        // The new source of the connection
        const sourceUrl = change.cell.source.id;
        // The new target of the connection
        const targetUrl = change.cell.target.id;
        // The new linked node
        const terminalUrl = change.terminal.id;
        let terminal = this.contents.find(element => element.url === terminalUrl) as IModelNode;
        if (terminal === undefined) {
            terminal = await this.dataService.readElement(terminalUrl, true) as IModelNode;
            this.contents = await this.dataService.readContents(this.model.url, true);
        }
        // The previously linked node
        const previousUrl = change.previous.id;
        let previous = this.contents.find(element => element.url === previousUrl) as IModelNode;
        if (previous === undefined) {
            previous = await this.dataService.readElement(previousUrl, true) as IModelNode;
            this.contents = await this.dataService.readContents(this.model.url, true);
        }

        connection.source.url = sourceUrl;
        connection.target.url = targetUrl;

        let field: 'incomingConnections' | 'outgoingConnections';
        if (sourceUrl === terminalUrl) {
            // Source was changed
            field = 'outgoingConnections';
        } else if (targetUrl === terminalUrl) {
            // Target was changed
            field = 'incomingConnections';
        }

        const connectionProxy = previous[field].find(proxy => proxy.url === connection.url);
        Arrays.remove(previous[field], connectionProxy);
        terminal[field].push(connectionProxy);

        const changeId = Id.uuid;
        this.dataService.updateElement(previous, true, changeId);
        this.dataService.updateElement(terminal, true, changeId);
        this.dataService.updateElement(connection, true, changeId);
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

    public get contents(): IContainer[] {
        return this._contents;
    }

    public get isValid(): boolean {
        return this.validationService.isValid(this.model);
    }

    public zoomIn(): void {
        this.graph.zoomIn();
    }

    public zoomOut(): void {
        this.graph.zoomOut();
    }

    public resetZoom(): void {
        this.graph.zoomActual();
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

    public get name(): string {
        if (!this.nameProvider) {
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
}
