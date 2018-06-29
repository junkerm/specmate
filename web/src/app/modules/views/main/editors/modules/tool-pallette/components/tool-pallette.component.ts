import { Component } from '@angular/core';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { EditorToolsService } from '../services/editor-tools.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { IContainer } from '../../../../../../../model/IContainer';
import { ToolBase } from '../tools/tool-base';
import { ElementProvider } from '../../graphical-editor/providers/properties/element-provider';
import { Id } from '../../../../../../../util/id';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
    selector: 'tool-pallette',
    templateUrl: 'tool-pallette.component.html',
    styleUrls: ['tool-pallette.component.css']
})
export class ToolPallette {

    constructor(private dataService: SpecmateDataService,
        private editorToolsService: EditorToolsService,
        private navigator: NavigatorService,
        private modal: ConfirmationModal,
        private selectedElementService: SelectedElementService,
        private translate: TranslateService) { }

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
        let message = this.translate.instant('doYouReallyWantToDeleteAll', {name: this.model.name});
        this.modal.open(message)
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
