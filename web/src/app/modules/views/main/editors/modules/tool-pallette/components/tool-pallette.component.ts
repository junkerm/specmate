import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { ElementProvider } from '../../graphical-editor/providers/properties/element-provider';
import { EditorToolsService } from '../services/editor-tools.service';
import { ToolBase } from '../tools/tool-base';

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

    public get tools(): ToolBase[] {
        return this.editorToolsService.tools;
    }

    public delete(event: MouseEvent): void {
        event.preventDefault();
        event.stopPropagation();
        let message = this.translate.instant('doYouReallyWantToDeleteAll', {name: this.model.name});
        let title = this.translate.instant('ConfirmationRequired');
        this.modal.confirmDelete(title, message)
            .then(() => this.dataService.readContents(this.model.url, true))
            .then((contents: IContainer[]) => this.removeAllElements(contents))
            .catch(() => {});
    }

    private removeAllElements(contents: IContainer[]): void {
        this.selectedElementService.deselect();
        let elementProvider = new ElementProvider(this.model, contents);
        this.dataService.clearModel(elementProvider.nodes, elementProvider.connections);
    }

    public get isVisible(): boolean {
        return this.tools && this.tools.length > 0;
    }
}
