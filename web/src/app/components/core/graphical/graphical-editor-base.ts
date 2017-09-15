import { Config } from "../../../config/config";
import { ISpecmatePositionableModelObject } from "../../../model/ISpecmatePositionableModelObject";
import { IContainer } from "../../../model/IContainer";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { ITool } from "./tools/i-tool";

export abstract class GraphicalEditorBase {

    public abstract get contents(): IContainer[];
    public abstract get isValid(): boolean;

    public abstract get tools(): ITool<ISpecmateModelObject>[];

    public get editorDimensions(): {width: number, height: number} {
        let dynamicWidth: number = Config.CEG_EDITOR_WIDTH;
        let dynamicHeight: number = Config.EDITOR_HEIGHT;
        
        let nodes: ISpecmatePositionableModelObject[] = this.contents.filter((element: IContainer) => {
            return (element as ISpecmatePositionableModelObject).x !== undefined && (element as ISpecmatePositionableModelObject).y !== undefined ;
        }) as ISpecmatePositionableModelObject[];

        for(let i = 0; i < nodes.length; i++) {
            let nodeX: number = nodes[i].x + (Config.CEG_NODE_WIDTH);
            if(dynamicWidth < nodeX) {
                dynamicWidth = nodeX;
            }

            let nodeY: number = nodes[i].y + (Config.CEG_NODE_HEIGHT);
            if(dynamicHeight < nodeY) {
                dynamicHeight = nodeY;
            }
        }
        return {width: dynamicWidth, height: dynamicHeight};
    }


}