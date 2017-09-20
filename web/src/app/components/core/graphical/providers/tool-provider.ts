import { ProviderBase } from "./provider-base";
import { ITool } from "../tools/i-tool";
import { CEGModel } from "../../../../model/CEGModel";
import { Type } from "../../../../util/Type";
import { MoveTool } from "../tools/move-tool";
import { NodeTool } from "../tools/node-tool";
import { ConnectionTool } from "../tools/connection-tool";
import { DeleteTool } from "../tools/delete-tool";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Process } from "../../../../model/Process";

export class ToolProvider extends ProviderBase {
    constructor(private model: IContainer, private dataService: SpecmateDataService) {
        super(model);
    }

    private _tools: ITool[];

    public get tools(): ITool[] {
        if(this._tools) {
            return this._tools;
        }
        if(Type.is(this.modelType, CEGModel)) {
            this.createToolsForCEGModel();
        }
        if(Type.is(this.modelType, Process)) {
            this.createToolsForProcess();
        }
        return this._tools;
    }

    private createToolsForCEGModel(): void {
        this._tools = [
            new MoveTool(),
            new NodeTool(this.model, this.dataService),
            new ConnectionTool(this.model, this.dataService),
            new DeleteTool(this.model, this.dataService)
        ];
    }

    private createToolsForProcess(): void {
        this._tools = [
            new MoveTool(),
        ];
    }
}