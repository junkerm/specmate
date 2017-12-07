import { Injectable } from '@angular/core';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { IContainer } from '../../../../../../../model/IContainer';
import { ToolProvider } from '../../graphical-editor/providers/properties/tool-provider';
import { ToolBase } from '../tools/tool-base';


@Injectable()
export class EditorToolsService {


    public activeTool: ToolBase;

    private model: IContainer;

    private providerMap: {[url: string]: ToolProvider }

    constructor(private dataService: SpecmateDataService,
        private navigator: NavigatorService,
        private selectedElementService: SelectedElementService) {
        this.init(this.navigator.currentElement);
        this.navigator.hasNavigated.subscribe((model: IContainer) => this.init(model));
    }

    private init(model: IContainer): void {
        if (!model) {
            return;
        }
        this.model = model;
        this.activateDefaultTool();
    }

    private get toolProvider(): ToolProvider {
        if (!this.model) {
            return undefined;
        }
        if (!this.providerMap) {
            this.providerMap = {};
        }
        if (!this.providerMap[this.model.url]) {
            this.providerMap[this.model.url] = new ToolProvider(this.model, this.dataService, this.selectedElementService);
        }
        return this.providerMap[this.model.url];
    }

    public get tools(): ToolBase[] {
        if (!this.toolProvider) {
            return undefined;
        }
        return this.toolProvider.tools;
    }

    public isActive(tool: ToolBase): boolean {
        return this.activeTool === tool;
    }

    public activate(tool: ToolBase): void {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    }

    public deactivate(tool: ToolBase): void {
        tool.deactivate();
    }

    public reset(): void {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
    }

    public activateDefaultTool(): void {
        this.dataService.readContents(this.model.url, true)
            .then((contents: IContainer[]) => {
                let defaultTool: ToolBase = this.toolProvider.getDefaultTool(contents);
                this.activate(defaultTool);
            });
    }

    public get cursor(): string {
        if (this.activeTool) {
            return this.activeTool.cursor;
        }
        return 'auto';
    }
}
