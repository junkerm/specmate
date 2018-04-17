import { Component, ViewChild } from '@angular/core';
import { SpecmateViewBase } from '../../../base/specmate-view-base';
import { Process } from '../../../../../../../model/Process';
import { IContainer } from '../../../../../../../model/IContainer';
import { GraphicalEditor } from '../../graphical-editor/components/graphical-editor.component';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
    selector: 'process-details',
    templateUrl: 'process-details.component.html',
    styleUrls: ['process-details.component.css']
})

export class ProcessDetails extends SpecmateViewBase {

    private model: Process;
    private contents: IContainer[];

    @ViewChild(GraphicalEditor)
    private editor: GraphicalEditor;

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService) {

        super(dataService, navigator, route, modal, translate);
    }

    protected onElementResolved(element: IContainer): void {
        this.model = element;
        this.dataService.readContents(this.model.url).then((contents: IContainer[]) => this.contents = contents);
    }

    public get isValid(): boolean {
        if (!this.editor) {
            return true;
        }
        return this.editor.isValid;
    }
}
