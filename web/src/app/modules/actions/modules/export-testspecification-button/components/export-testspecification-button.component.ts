import { Component, OnInit, Input } from '@angular/core';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { TestCase } from '../../../../../model/TestCase';
import { TestParameter } from '../../../../../model/TestParameter';
import { ParameterAssignment } from '../../../../../model/ParameterAssignment';
import { Url } from '../../../../../util/url';
import { Type } from '../../../../../util/type';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { saveAs } from 'file-saver';

@Component({
    moduleId: module.id.toString(),
    selector: 'export-testspecification-button',
    templateUrl: 'export-testspecification-button.component.html',
    styleUrls: ['export-testspecification-button.component.css']
})

export class ExportTestspecificationButton {

    @Input()
    public testSpecification: TestSpecification;

    private contents: IContainer[];
    private testCases: TestCase[];
    private testParameters: TestParameter[];
    private parameterAssignments: ParameterAssignment[];
    private finalCsvString: string;

    constructor(
        private dataService: SpecmateDataService,
        private validation: ValidationService) { }

    // Export Function
    public async exportTestSpecification(): Promise<void> {
        if (!this.enabled) {
          return;
        }
        const contents = await this.dataService.readContents(this.testSpecification.url);
        this.contents = contents;
        await this.loadTestParameters();
        await this.loadTestCases();
        await Promise.all(this.loadParameterAssignments());
        this.prepareExportString();
        this.createDownloadFile();
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
        }
        return promiseArray;
    }

    private createDownloadFile(): void {
      const blob = new Blob(['\ufeff', this.finalCsvString], { type: 'text/csv;charset=utf-8;' });
      saveAs(blob, this.testSpecification.name + '.csv');
    }

    public get enabled(): boolean {
        return this.isValid();
    }

    private isValid(): boolean {
      if (this.testSpecification === undefined) {
          return false;
      }
      return this.validation.isValid(this.testSpecification, this.contents);
    }
}
