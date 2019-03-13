import { ElementFactoryBase } from './element-factory-base';
import { TestCase } from '../model/TestCase';
import { SpecmateDataService } from '../modules/data/modules/data-service/services/specmate-data.service';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { Config } from '../config/config';
import { TestParameter } from '../model/TestParameter';
import { ParameterAssignmentFactory } from './parameter-assignment-factory';
import { TranslateService } from '@ngx-translate/core';

export class TestCaseFactory extends ElementFactoryBase<TestCase> {

    constructor(dataService: SpecmateDataService, private preLoadContents: boolean) {
        super(dataService);
    }

    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string, consistent = true): Promise<TestCase> {
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let testCase: TestCase = new TestCase();
        testCase.name = name || Config.TESTCASE_NAME + ' ' + ElementFactoryBase.getDateStr();
        testCase.id = id;
        testCase.url = url;
        testCase.consistent = consistent;

        let preloadTask: Promise<IContainer[]>;

        if (this.preLoadContents) {
            preloadTask = this.loadContents(parent);
        } else {
            preloadTask = Promise.resolve().then(() => testCase.position = 0).then(() => this.contents = []);
        }

        return preloadTask.then((contents: IContainer[]) => testCase.position = this.otherTestCases.length)
        .then(() => this.dataService.createElement(testCase, true, compoundId))
        .then(() => this.createParameterAssignments(testCase, compoundId))
        .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
        .then(() => testCase);
    }

    private get otherTestCases(): IContainer[] {
        return this.getContentsOfType(TestCase);
    }

    private get testParameters(): IContainer[] {
        return this.getContentsOfType(TestParameter);
    }

    private createParameterAssignments(testCase: TestCase, compoundId: string): Promise<void> {
        let createParameterAssignmentTask: Promise<void> = Promise.resolve();
        let testParameters = this.testParameters;
        for (let i = 0; i < testParameters.length; i++) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                let currentTestParameter: TestParameter = testParameters[i] as TestParameter;
                let parameterAssignmentFactory: ParameterAssignmentFactory =
                    new ParameterAssignmentFactory(this.dataService, currentTestParameter);
                return parameterAssignmentFactory.create(testCase, false, compoundId);
            }).then(() => Promise.resolve());
        }
        return createParameterAssignmentTask;
    }
}
