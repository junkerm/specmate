import { Component, OnInit, Input } from '@angular/core';
import { TestProcedure } from '../../../../../model/TestProcedure';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { AccessRights } from '../../../../../model/AccessRights';
import { UserToken } from '../../../../views/main/authentication/base/user-token';
import { TestSpecification } from '../../../../../model/TestSpecification';


@Component({
    moduleId: module.id.toString(),
    selector: 'export-test-specification-button',
    templateUrl: 'export-test-specification-button.component.html',
    styleUrls: ['export-test-specification-button.component.css']
})

export class ExportTestSpecificationButton implements OnInit {

    @Input()
    public testSpecification: TestSpecification;

    private contents: IContainer[];

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private validation: ValidationService,
        private translate: TranslateService,
        private auth: AuthenticationService) { }

    ngOnInit(): void {
        this.dataService.readContents(this.testSpecification.url).then((contents: IContainer[]) => this.contents = contents);
    }

    /** Pushes or updates a test procedure */
    /*public pushTestProcedure(): void {
        if (!this.canExport) {
            return;
        }
        this.modal.confirmSave().then( () =>
            this.dataService.commit(this.translate.instant('saveBeforeTestprocedureExport')).then( () =>
                this.dataService.performOperations(this.testProcedure.url, 'syncalm')
                .then((result) => {
                        if (result) {
                            this.modal
                                .openOk(this.translate.instant('successful'), this.translate.instant('procedureExportedSuccessfully'));
                        } else {
                            this.modal.openOk(this.translate.instant('failed'), this.translate.instant('procedureExportFailed'));
                        }
                    }
                )
            )
        );
    }*/

    /*public get canExport(): boolean {
        return this.isValid() && this.isAuthorized();
    }

    public get buttonTitle(): string {
        if (!this.isValid()) {
            return 'procedureNotValid';
        }
        if (!this.isAuthorized()) {
            return 'notAuthorizedToExport';
        }
        return 'exportTestprocedure';
    }

    private isValid(): boolean {
        return this.validation.isValid(this.testProcedure) && this.validation.allValid(this.contents);
    }

    private isAuthorized(): boolean {
        if (UserToken.isInvalid(this.auth.token)) {
            return false;
        }
        let exp: string = this.auth.token.session.TargetSystem;
        return exp === 'ALL' || exp === 'WRITE';
    }*/
}
