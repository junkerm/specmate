import { Component, Input } from "@angular/core";
import { EditorToolsService } from "../../../../services/editor/editor-tools.service";
import { IContainer } from "../../../../model/IContainer";
import { ConfirmationModal } from "../../../../services/notification/confirmation-modal.service";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { Id } from "../../../../util/Id";
import { ElementProvider } from "../providers/element-provider";
import { NavigatorService } from "../../../../services/navigation/navigator.service";
import { ToolBase } from "./tool-base";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";

@Component({
    moduleId: module.id,
    selector: 'tool-pallette',
    templateUrl: 'tool-pallette.component.html',
    styleUrls: ['tool-pallette.component.css']
})
export class ToolPallette {

    constructor(private dataService: SpecmateDataService, private editorToolsService: EditorToolsService, private navigator: NavigatorService, private modal: ConfirmationModal, private selectedElementService: SelectedElementService) { }

    private get model(): IContainer {
        return this.navigator.currentElement;
    }

    public onClick(tool: ToolBase, event: MouseEvent): void {
        event.preventDefault();
        event.stopPropagation();
        this.activate(tool);
    }

    public get tools(): ToolBase[] {
        return this.editorToolsService.tools;
    }

    public isActive(tool: ToolBase): boolean {
        return this.editorToolsService.isActive(tool);
    }

    public activate(tool: ToolBase): void {
        this.editorToolsService.activate(tool);
    }
    
    private delete(event: MouseEvent): void {
        event.preventDefault();
        event.stopPropagation();
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(() => this.dataService.readContents(this.model.url, true))
            .then((contents: IContainer[]) => this.removeAllElements(contents))
            .catch(() => {});
    }

    private removeAllElements(contents: IContainer[]): void {
        this.selectedElementService.deselect();
        let elementProvider = new ElementProvider(this.model, contents);
        let compoundId: string = Id.uuid;
        for (let i = elementProvider.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(elementProvider.connections[i].url, true, compoundId);
        }
        for (let i = elementProvider.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(elementProvider.nodes[i].url, true, compoundId);
        }
    }

    public get isVisible(): boolean {
        return this.tools && this.tools.length > 0;
    }
}
