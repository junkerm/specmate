import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../model/IContainer';
import { TestProcedure } from '../../../../../model/TestProcedure';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { UserToken } from '../../../../views/main/authentication/base/user-token';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';


@Component({
    moduleId: module.id.toString(),
    selector: 'export-testprocedure-button',
    templateUrl: 'export-testprocedure-button.component.html',
    styleUrls: ['export-testprocedure-button.component.css']
})

export class ExportTestprocedureButton implements OnInit {

    @Input()
    public testProcedure: TestProcedure;

    private contents: IContainer[];

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private validation: ValidationService,
        private translate: TranslateService,
        private auth: AuthenticationService) { }

    ngOnInit(): void {
        this.dataService.readContents(this.testProcedure.url).then((contents: IContainer[]) => this.contents = contents);
    }

    /** Pushes or updates a test procedure */
    public pushTestProcedure(): void {
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
    }

    public get canExport(): boolean {
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
    }
}
