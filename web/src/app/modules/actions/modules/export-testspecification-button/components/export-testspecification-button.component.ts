import { Component, OnInit, Input } from '@angular/core';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { TestCase } from '../../../../../model/TestCase';
import { TestParameter } from '../../../../../model/TestParameter';
import { ParameterAssignment } from '../../../../../model/ParameterAssignment';
import { Url } from '../../../../../util/url';
import { Type } from '../../../../../util/type';
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

    }

    private testCases: TestCase[];
    private testParameters: TestParameter[];
    private parameterAssignments: ParameterAssignment[];
    private finalCsvString: string;

    /** Pushes or updates a test procedure */
    public exportTestSpecification(): void {
        // TODO check if isAuthorized
        this.dataService.readContents(this.testSpecification.url).then((contents: IContainer[]) => {
            this.contents = contents;
            this.loadTestParameters();
            this.loadTestCases();
            Promise.all(this.loadParameterAssignments()).then(() => {
              this.prepareExportString();
              this.createDownloadFile();
            });
        });
    }

    private prepareExportString(): void {
        this.pushHeaders();
        this.pushRows();
    }

    private pushHeaders(): void {
        let header = 'Test Cases,';
        if (this.testParameters) {
          for (let i = 0 ; i < this.testParameters.length ; i++) {
            if (this.testParameters[i].type == 'INPUT') {
                header += this.testParameters[i].type + ' - ' + this.testParameters[i].name + ', ';
            }
          }
          for (let i = 0 ; i < this.testParameters.length ; i++) {
            if (this.testParameters[i].type == 'OUTPUT') {
                header += this.testParameters[i].type + ' - ' + this.testParameters[i].name + ', ';
            }
          }
        }
        header += '\n';
        this.finalCsvString = header;
    }

    private pushRows(): void {
        let testCasesString = '';
        if (this.testCases) {
          for (let i = 0 ; i < this.testCases.length ; i++) {
            testCasesString += this.testCases[i].name + ', ';
            for (let j = 0 ; j < this.testParameters.length; j++) {
              let assignmentsList = this.getAssignments(this.testParameters[j]);
                for (let k = 0 ; k < assignmentsList.length; k++) {
                  if (Url.parent(assignmentsList[k].url) == this.testCases[i].url) {
                    testCasesString += assignmentsList[k].condition + ', ';
                    break;
                  }
                }
            }
            testCasesString += '\n';
          }
        }
        this.finalCsvString += testCasesString;
    }
    private getAssignments(testParameter: TestParameter): ParameterAssignment[] {
      let assignmentsList: ParameterAssignment[];
      assignmentsList = [];
      for (let i = 0 ; i < this.parameterAssignments.length; i++) {
        if (this.parameterAssignments[i].parameter.url == testParameter.url) {
          assignmentsList.push(this.parameterAssignments[i]);
        }
      }
      return assignmentsList;
    }

    private createDownloadFile(): void {
      const blob = new Blob([this.finalCsvString]);
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

    private loadTestCases(): void {
         this.testCases = this.contents.filter((element: IContainer) => Type.is(element, TestCase))
             .map((element: IContainer) => element as TestCase);
    }
    private loadTestParameters(): Promise<void> {
      let loadTestParametersTask: Promise<void> = Promise.resolve();
      loadTestParametersTask = loadTestParametersTask.then(() => {
        this.testParameters = this.contents.filter((element: IContainer) => Type.is(element, TestParameter))
           .map((element: IContainer) => element as TestParameter);
      });
      return loadTestParametersTask;
    }
    private loadParameterAssignments(): Promise<void>[] {
        let testCases: TestCase[] = this.testCases;
        this.parameterAssignments = [];
        let promiseArray: Promise<void>[];
        promiseArray = [];

        let loadParameterAssignmentsTask: Promise<void> = Promise.resolve();
        for (let i = 0; i < testCases.length; i++) {
            let currentTestCase: TestCase = testCases[i];
            loadParameterAssignmentsTask = loadParameterAssignmentsTask.then(() => {
                return this.dataService.readContents(currentTestCase.url)
                    .then((contents: IContainer[]) =>
                        contents.forEach((element: IContainer) => {
                          if (element.className == 'ParameterAssignment') {
                                this.parameterAssignments.push(element as ParameterAssignment);
                          }
                        }));
            });
            promiseArray.push(loadParameterAssignmentsTask);
            // promiseArray.push(new Promise((resolve, reject) => {
            //   resolve.then((contents) => {
            //     contents.forEach((element: IContainer) => {
            //       if (element.className == 'ParameterAssignment') {
            //             this.parameterAssignments.push(element as ParameterAssignment);
            //       }
            //   });
            //   });
            //
            // }));
            // }));
        }

        return promiseArray;
        // return loadParameterAssignmentsTask;
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
