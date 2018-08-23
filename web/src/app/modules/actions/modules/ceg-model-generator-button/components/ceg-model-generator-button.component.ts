import { Component, Input } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { CEGModel } from '../../../../../model/CEGModel';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { Requirement } from '../../../../../model/Requirement';

@Component({
    moduleId: module.id.toString(),
    selector: 'ceg-model-generator-button',
    templateUrl: 'ceg-model-generator-button.component.html',
    styleUrls: ['ceg-model-generator-button.component.css']
})

export class CegModelGeneratorButton {

    private contents: IContainer[];

    private _model: CEGModel ;

    private _requirement: Requirement ;

    public get requirement(): Requirement {
        return this._requirement;
    }

    @Input()
    public set requirement(requirement: Requirement) {
        if (!requirement) {
            return;
        }
        this._requirement = requirement;
    }
    public get model(): CEGModel  {
        return this._model;
    }

    @Input()
    public set model(model: CEGModel) {
        if (!model) {
            return;
        }
        this._model = model;
        this.dataService.readContents(model.url).then((contents: IContainer[]) => {
            this.contents = contents;
        });
    }

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private navigator: NavigatorService,
        private translate: TranslateService) { }

    public async generate(): Promise<void> {
        if (!this.enabled) {
            return;
        }
        this.modal.openOkCancel(this.translate.instant('CEGGenerator.confirm'), this.translate.instant('CEGGenerator.confirmationTitle'))
            .then(() => this.dataService.performOperations(this.model.url, 'generateModel'))
            // .then(() => this.dataService.deleteCachedContents(this.model.url))
            // .then(() => this.dataService.readContents(this.model.url, false))
            .catch(() => { });
    }

    public get enabled(): boolean {
        if (this.model === undefined) {
            return false;
        }
        return true;
    }
}
