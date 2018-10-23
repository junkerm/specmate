import { Component, OnInit, Input } from '@angular/core';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { TestCase } from '../../../../../model/TestCase';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { AccessRights } from '../../../../../model/AccessRights';
import { UserToken } from '../../../../views/main/authentication/base/user-token';


@Component({
    moduleId: module.id.toString(),
    selector: 'export-testspecification-button',
    templateUrl: 'export-testspecification-button.component.html',
    styleUrls: ['export-testspecification-button.component.css']
})

export class ExportTestspecificationButton implements OnInit {

    @Input()
    public testSpecification: TestSpecification;
    private contents: IContainer[];

    constructor(
      private dataService: SpecmateDataService,
      //   private modal: ConfirmationModal,
        private validation: ValidationService,
        private translate: TranslateService,
        private auth: AuthenticationService
      ) { }

    ngOnInit(): void {
        this.dataService.readContents(this.testSpecification.url).then((contents: IContainer[]) => this.contents = contents);
    }

    /** Pushes or updates a test procedure */
    public pushTestSpecification(): void {
      // TODO check if isAuthorized
        let thingsToExport: string;

        thingsToExport = this.exportTestSpecifcationFromIContainer();

        const blob = new Blob([thingsToExport]);
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, 'Book.csv');
        } else {
            const a = document.createElement('a');
            a.href = url;
            a.download = 'Book.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    private exportTestSpecifcationFromIContainer(): string {
        let testCaseAsString = 'Hello,';
        for (let i = 0 ; i <  this.contents.length ; i++) {
            if (this.contents[i].className == 'TestCase') {
              testCaseAsString += this.contents[i].name;
              // let myTestCase:  TestCase = this.contents[i];
              // for (let x = 0 ; x < this.myTestCase.inputParameters.length ; x++) {
              //   testCaseAsString += this.myTestCase.inputParameters[x].name  + ',';
              // }
              let mycontents: IContainer[];
              this.dataService.readContents(this.contents[i].url).then((tempContent: IContainer[]) => mycontents = tempContent);
              for (let j = 0 ; j <  mycontents.length ; j++) {
                console.log(mycontents[j].className);
                console.log(mycontents[j].name);
              }
            }

        }

        // console.log(this.contents.length);
        // console.log(this.testSpecification.url);

        // for (let i = 0 ; i <  this.contents.length ; i++) {
        //   // testCaseAsString.concat('weeds');
        //   console.log(this.contents[i].className);
        //   // console.log('I am inside');
        //   // console.log(this.contents[i].name);
        // }
        return testCaseAsString;
    }

    public get canExport(): boolean {
        return this.isValid() && this.isAuthorized();
    }

    public get buttonTitle(): string {
        if (!this.isValid()) {
            return 'specificationNotValid';
        }
        if (!this.isAuthorized()) {
            return 'notAuthorizedToExport';
        }
        return 'exportTestspecification';
    }

    private isValid(): boolean {
        return this.validation.isValid(this.testSpecification) && this.validation.allValid(this.contents);
    }

    private isAuthorized(): boolean {
        if (UserToken.isInvalid(this.auth.token)) {
            return false;
        }
        let exp: string = this.auth.token.session.TargetSystem;
        return exp === 'ALL' || exp === 'WRITE';
    }
}
