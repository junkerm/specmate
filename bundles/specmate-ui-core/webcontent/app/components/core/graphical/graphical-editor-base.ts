import { Config } from "../../../config/config";
import { ISpecmatePositionableModelObject } from "../../../model/ISpecmatePositionableModelObject";
import { IContainer } from "../../../model/IContainer";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";

export abstract class GraphicalEditorBase {

    public abstract get contents(): IContainer[];
    public abstract get isValid(): boolean;

    public isMaximized: boolean = false;

    public maximize(): void {
        this.isMaximized = true;
    }

    public unMaximize(): void {
        this.isMaximized = false;
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


}