import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ClipboardService } from '../../../tool-pallette/services/clipboard-service';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';
import { CEGConnectionTool } from '../../../tool-pallette/tools/ceg/ceg-connection-tool';
import { CEGDeleteTool } from '../../../tool-pallette/tools/ceg/ceg-delete-tool';
import { CEGNodeTool } from '../../../tool-pallette/tools/ceg/ceg-node-tool';
import { DecisionTool } from '../../../tool-pallette/tools/process/decision-tool';
import { EndTool } from '../../../tool-pallette/tools/process/end-tool';
import { ProcessConnectionTool } from '../../../tool-pallette/tools/process/process-connection-tool';
import { ProcessDeleteTool } from '../../../tool-pallette/tools/process/process-delete-tool';
import { StartTool } from '../../../tool-pallette/tools/process/start-tool';
import { StepTool } from '../../../tool-pallette/tools/process/step-tool';
import { ToolBase } from '../../../tool-pallette/tools/tool-base';
import { ProviderBase } from './provider-base';

export class ToolProvider extends ProviderBase {

    private _tools: ToolBase[];

    constructor(
        private model: IContainer,
        private dataService: SpecmateDataService,
        private selectedElementService: SelectedElementService,
        private translate: TranslateService,
        private rectService: MultiselectionService,
        private clipboardService: ClipboardService) {
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
            new CEGNodeTool(this.dataService, this.selectedElementService, this.model),
            new CEGConnectionTool(this.dataService, this.selectedElementService, this.model),
            new CEGDeleteTool(this.model, this.dataService, this.selectedElementService)
        ];
    }

    private createToolsForProcess(): void {
        this._tools = [
            new StepTool(this.dataService, this.selectedElementService, this.model),
            new DecisionTool(this.model, this.dataService, this.selectedElementService),
            new StartTool(this.model, this.dataService, this.selectedElementService),
            new EndTool(this.model, this.dataService, this.selectedElementService),
            new ProcessConnectionTool(this.dataService, this.selectedElementService, this.model),
            new ProcessDeleteTool(this.model, this.dataService, this.selectedElementService)
        ];
    }

    public getDefaultTool(contents: IContainer[]): ToolBase {
        return contents && contents.length > 0 ? this.tools[0] : this.tools[1];
    }
}
