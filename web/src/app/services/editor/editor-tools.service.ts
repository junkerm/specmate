import { Injectable } from '@angular/core';
import { SpecmateDataService } from '../data/specmate-data.service';
import { NavigatorService } from '../navigation/navigator.service';
import { ToolProvider } from '../../components/core/graphical/providers/tool-provider';
import { ITool } from '../../components/core/graphical/tools/i-tool';
import { IContainer } from '../../model/IContainer';


@Injectable()
export class EditorToolsService {
    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) {
        this.navigator.hasNavigated.subscribe((model: IContainer) => {
            this.model = model;
            this.activateDefaultTool();
        });
    }

    private model: IContainer;

    private providerMap: {[url: string] : ToolProvider }

    private get toolProvider(): ToolProvider {
        if(!this.model) {
            return undefined;
        }
        if(!this.providerMap) {
            this.providerMap = {};
        }
        if(!this.providerMap[this.model.url]) {
            this.providerMap[this.model.url] = new ToolProvider(this.model, this.dataService);
        }
        return this.providerMap[this.model.url];
    }

    public get tools(): ITool[] {
        if(!this.toolProvider) {
            return undefined;
        }
        return this.toolProvider.tools;
    }

    public activeTool: ITool;

    public isActive(tool: ITool): boolean {
        return this.activeTool === tool;
    }

    public activate(tool: ITool): void {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    }

    public deactivate(tool: ITool): void {
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
            .then((contents: IContainer[]) => this.activate(this.toolProvider.getDefaultTool(contents)));
    }

    public get cursor(): string {
        if(this.activeTool) {
            return this.activeTool.cursor;
        }
        return 'auto';
    }
}
