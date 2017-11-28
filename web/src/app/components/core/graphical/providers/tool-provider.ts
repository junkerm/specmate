import { ProviderBase } from "./provider-base";
import { ITool } from "../tools/i-tool";
import { CEGModel } from "../../../../model/CEGModel";
import { Type } from "../../../../util/Type";
import { MoveTool } from "../tools/move-tool";
import { CEGNodeTool } from "../tools/ceg/ceg-node-tool";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { Process } from "../../../../model/Process";
import { StepTool } from "../tools/process/step-tool";
import { DecisionTool } from "../tools/process/decision-tool";
import { StartTool } from "../tools/process/start-tool";
import { EndTool } from "../tools/process/end-tool";
import { CEGConnectionTool } from "../tools/ceg/ceg-connection-tool";
import { ProcessConnectionTool } from "../tools/process/process-connection-tool";
import { CEGDeleteTool } from "../tools/ceg/ceg-delete-tool";
import { ProcessDeleteTool } from "../tools/process/process-delete-tool";

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
        else if(Type.is(this.modelType, Process)) {
            this.createToolsForProcess();
        }
        else {
            this.createEmptyTools();
        }

        return this._tools;
    }

    private createEmptyTools(): void {
        this._tools = [];
    }

    private createToolsForCEGModel(): void {
        this._tools = [
            new MoveTool(),
            new CEGNodeTool(this.model, this.dataService),
            new CEGConnectionTool(this.model, this.dataService),
            new CEGDeleteTool(this.model, this.dataService)
        ];
    }

    private createToolsForProcess(): void {
        this._tools = [
            new MoveTool(),
            new StepTool(this.model, this.dataService),
            new DecisionTool(this.model, this.dataService),
            new StartTool(this.model, this.dataService),
            new EndTool(this.model, this.dataService),
            new ProcessConnectionTool(this.model, this.dataService),
            new ProcessDeleteTool(this.model, this.dataService)
        ];
    }
}