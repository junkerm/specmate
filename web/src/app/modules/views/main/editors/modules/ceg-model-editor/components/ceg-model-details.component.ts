import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SpecmateViewBase } from '../../../base/specmate-view-base';
import { GraphicalEditor } from '../../graphical-editor/components/graphical-editor.component';

@Component({
    moduleId: module.id.toString(),
    selector: 'ceg-model-details-editor',
    templateUrl: 'ceg-model-details.component.html',
    styleUrls: ['ceg-model-details.component.css']
})

export class CEGModelDetails extends SpecmateViewBase {

    public model: CEGModel;
    private contents: IContainer[];

    @ViewChild(GraphicalEditor)
    private editor: GraphicalEditor;

    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService) {

        super(dataService, navigator, route, modal, translate);
    }

    protected onElementResolved(element: IContainer): void {
        this.model = <CEGModel>element;
        this.dataService.readContents(this.model.url).then((contents: IContainer[]) => this.contents = contents);
    }

    protected get isValid(): boolean {
        if (!this.editor) {
            return true;
        }
        return this.editor.isValid;
    }
}
