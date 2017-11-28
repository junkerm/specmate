import { Injectable } from '@angular/core';
import { SpecmateDataService } from '../data/specmate-data.service';
import { NavigatorService } from '../navigation/navigator.service';
import { ToolProvider } from '../../components/core/graphical/providers/tool-provider';
import { ITool } from '../../components/core/graphical/tools/i-tool';
import { IContainer } from '../../model/IContainer';


@Injectable()
export class EditorToolsService {
    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) { }

    private get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    private providerMap: {[url: string] : ToolProvider }

    private get toolProvider(): ToolProvider {
        if(!this.providerMap) {
            this.providerMap = {};
        }
        if(!this.providerMap[this.currentElement.url]) {
            this.providerMap[this.currentElement.url] = new ToolProvider(this.currentElement, this.dataService);
        }
        return this.providerMap[this.currentElement.url];
    }

    public get tools(): ITool[] {
        return this.toolProvider.tools;
    }

    public activate(tool: ITool): void {

    }

    public deactivate(tool: ITool): void {
        
    }

    public reset(): void {

    }

    public activateDefaultTool(): void {

    }
}
