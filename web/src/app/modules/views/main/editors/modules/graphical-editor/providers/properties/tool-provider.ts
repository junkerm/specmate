import { ProviderBase } from './provider-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ToolBase } from '../../../tool-pallette/tools/tool-base';
import { MoveTool } from '../../../tool-pallette/tools/move-tool';
import { CEGNodeTool } from '../../../tool-pallette/tools/ceg/ceg-node-tool';
import { CEGConnectionTool } from '../../../tool-pallette/tools/ceg/ceg-connection-tool';
import { CEGDeleteTool } from '../../../tool-pallette/tools/ceg/ceg-delete-tool';
import { StepTool } from '../../../tool-pallette/tools/process/step-tool';
import { DecisionTool } from '../../../tool-pallette/tools/process/decision-tool';
import { StartTool } from '../../../tool-pallette/tools/process/start-tool';
import { EndTool } from '../../../tool-pallette/tools/process/end-tool';
import { ProcessConnectionTool } from '../../../tool-pallette/tools/process/process-connection-tool';
import { ProcessDeleteTool } from '../../../tool-pallette/tools/process/process-delete-tool';
import { TranslateService } from '@ngx-translate/core';

export class ToolProvider extends ProviderBase {

    private _tools: ToolBase[];

    constructor(
        private model: IContainer,
        private dataService: SpecmateDataService,
        private selectedElementService: SelectedElementService,
        private translate: TranslateService) {
        super(model);
    }

    public get tools(): ToolBase[] {
        if (this._tools) {
            return this._tools;
        }

        if (this.isCEGModel) {
            this.createToolsForCEGModel();
        } else if (this.isProcessModel) {
            this.createToolsForProcess();
        } else {
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
