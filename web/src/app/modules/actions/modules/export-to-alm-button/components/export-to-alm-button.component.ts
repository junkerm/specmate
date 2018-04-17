import { Component, OnInit, Input } from '@angular/core';
import { TestProcedure } from '../../../../../model/TestProcedure';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { TranslateService } from '@ngx-translate/core';


@Component({
    moduleId: module.id.toString(),
    selector: 'export-to-alm-button',
    templateUrl: 'export-to-alm-button.component.html',
    styleUrls: ['export-to-alm-button.component.css']
})

export class ExportToALMButton implements OnInit {

    @Input()
    public testProcedure: TestProcedure;

    private contents: IContainer[];

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private validation: ValidationService,
        private translate: TranslateService) { }

    ngOnInit(): void {
        this.dataService.readContents(this.testProcedure.url).then((contents: IContainer[]) => this.contents = contents);
    }

    /** Pushes or updates a test procedure to HP ALM */
    public pushTestProcedure(): void {
        if (!this.isValid) {
            return;
        }
        this.modal.confirmSave().then( () =>
            this.dataService.commit(this.translate.instant('saveBeforeALMExport')).then( () =>
                this.dataService.performOperations(this.testProcedure.url, 'syncalm')
                .then((result) => {
                        if (result) {
                            this.modal.open(this.translate.instant('procedureExportedSuccessfully'), false);
                        }
                    }
                )
            )
        );
    }

    private get isValid(): boolean {
        return this.validation.isValid(this.testProcedure) && this.validation.allValid(this.contents);
    }
}
