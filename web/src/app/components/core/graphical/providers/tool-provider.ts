import { ProviderBase } from "./provider-base";
import { Type } from "../../../../util/Type";
import { MoveTool } from "../tools/move-tool";
import { CEGNodeTool } from "../tools/ceg/ceg-node-tool";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { IContainer } from "../../../../model/IContainer";
import { StepTool } from "../tools/process/step-tool";
import { DecisionTool } from "../tools/process/decision-tool";
import { StartTool } from "../tools/process/start-tool";
import { EndTool } from "../tools/process/end-tool";
import { CEGConnectionTool } from "../tools/ceg/ceg-connection-tool";
import { ProcessConnectionTool } from "../tools/process/process-connection-tool";
import { CEGDeleteTool } from "../tools/ceg/ceg-delete-tool";
import { ProcessDeleteTool } from "../tools/process/process-delete-tool";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";
import { ToolBase } from "../tools/tool-base";

export class ToolProvider extends ProviderBase {
    constructor(private model: IContainer, private dataService: SpecmateDataService, private selectedElementService: SelectedElementService) {
        super(model);
    }

    private _tools: ToolBase[];

    public get tools(): ToolBase[] {
        if(this._tools) {
            return this._tools;
        }

        if(this.isCEGModel) {
            this.createToolsForCEGModel();
        }
        else if(this.isProcessModel) {
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
            new MoveTool(this.selectedElementService),
            new CEGNodeTool(this.model, this.dataService, this.selectedElementService),
            new CEGConnectionTool(this.model, this.dataService, this.selectedElementService),
            new CEGDeleteTool(this.model, this.dataService, this.selectedElementService)
        ];
    }

    private createToolsForProcess(): void {
        this._tools = [
            new MoveTool(this.selectedElementService),
            new StepTool(this.model, this.dataService, this.selectedElementService),
            new DecisionTool(this.model, this.dataService, this.selectedElementService),
            new StartTool(this.model, this.dataService, this.selectedElementService),
            new EndTool(this.model, this.dataService, this.selectedElementService),
            new ProcessConnectionTool(this.model, this.dataService, this.selectedElementService),
            new ProcessDeleteTool(this.model, this.dataService, this.selectedElementService)
        ];
    }

    public getDefaultTool(contents: IContainer[]): ToolBase {
        return contents && contents.length > 0 ? this.tools[0] : this.tools[1];
    }
}