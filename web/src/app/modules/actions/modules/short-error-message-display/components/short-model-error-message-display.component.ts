import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Config } from '../../../../../config/config';
import { ElementFactoryBase } from '../../../../../factory/element-factory-base';
import { CEGModel } from '../../../../../model/CEGModel';
import { IContainer } from '../../../../../model/IContainer';
import { Process } from '../../../../../model/Process';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Id } from '../../../../../util/id';
import { Url } from '../../../../../util/url';
import { ValidationResult } from '../../../../../validation/validation-result';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'short-model-error-message-display',
    templateUrl: 'short-model-error-message-display.component.html',
    styleUrls: ['short-model-error-message-display.component.css']
})

export class ShortModelErrorMessageDisplay {

    private contents: IContainer[];

    private _model: CEGModel | Process;

    public get model(): CEGModel | Process {
        return this._model;
    }

    @Input()
    public set model(model: CEGModel | Process) {
        if (!model) {
            return;
        }
        this._model = model;
        this.dataService.readContents(model.url).then((contents: IContainer[]) => {
            this.contents = contents;
        });
    }

    constructor(private dataService: SpecmateDataService,
        private validator: ValidationService) { }

    public get hasErrors(): boolean {
        if (this.model === undefined) {
            return false;
        }
        return !this.validator.isValid(this.model, this.contents);
    }
}
