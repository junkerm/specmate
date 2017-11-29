import { Config } from "../../../config/config";
import { ISpecmatePositionableModelObject } from "../../../model/ISpecmatePositionableModelObject";
import { IContainer } from "../../../model/IContainer";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { EditorToolsService } from "../../../services/editor/editor-tools.service";
import { ToolBase } from "./tools/tool-base";

export abstract class GraphicalEditorBase {

    public abstract get contents(): IContainer[];
    public abstract get isValid(): boolean;

    public isMaximized: boolean = false;
    public isGridShown: boolean = true;

    protected zoom: number = 1;

    constructor(protected editorToolsService: EditorToolsService) {

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

    public maximize(): void {
        this.isMaximized = true;
    }

    public unMaximize(): void {
        this.isMaximized = false;
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

}