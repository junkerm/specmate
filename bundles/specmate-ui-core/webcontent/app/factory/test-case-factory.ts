import { Config } from '../config/config';
import { Url } from '../util/Url';
import { Id } from '../util/Id';
import { ElementFactoryBase } from "./element-factory-base";
import { TestCase } from "../model/TestCase";
import { IContainer } from "../model/IContainer";
import { Type } from '../util/Type';
import { TestParameter } from '../model/TestParameter';
import { TestInputParameterFactory } from './test-input-parameter-factory';
import { TestOutputParameterFactory } from './test-output-parameter-factory';
import { ParameterAssignmentFactory } from './parameter-assignment-factory';
import { SpecmateDataService } from '../services/data/specmate-data.service';

export class TestCaseFactory extends ElementFactoryBase<TestCase> {
    
    constructor(dataService: SpecmateDataService, private preLoadContents: boolean) {
        super(dataService);
    }

    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<TestCase> {
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let testCase: TestCase = new TestCase();
        testCase.name = Config.TESTCASE_NAME;
        testCase.id = id;
        testCase.url = url;

        let preloadTask: Promise<IContainer[]>;

        if(this.preLoadContents) {
            preloadTask = this.loadContents(parent);
        } else {
            preloadTask = Promise.resolve().then(() => testCase.position = 0).then(() => this.contents = []);
        }

        return preloadTask.then((contents: IContainer[]) => testCase.position = this.otherTestCases.length)
        .then(() => this.initializeTestParameters(parent, compoundId))
        .then(() => this.dataService.createElement(testCase, true, compoundId))
        .then(() => this.createParameterAssignments(testCase, compoundId))
        .then(() => commit ? this.dataService.commit('Create Test Case') : Promise.resolve())
        .then(() => testCase);
    }
    
    private get otherTestCases(): IContainer[] {
        return this.getContentsOfType(TestCase);
    }

    private get testParameters(): IContainer[] {
        return this.getContentsOfType(TestParameter);
    }

    private initializeTestParameters(parent: IContainer, compoundId: string): Promise<void> {
        if(this.testParameters && this.testParameters.length > 0) {
            return Promise.resolve();
        }
        let inputFactory: TestInputParameterFactory = new TestInputParameterFactory(this.dataService);
        let outputFactory: TestOutputParameterFactory = new TestOutputParameterFactory(this.dataService);
        return inputFactory.create(parent, false, compoundId)
            .then(() => outputFactory.create(parent, false, compoundId))
            .then(() => this.loadContents(parent))
            .then(() => Promise.resolve());
    }

    private createParameterAssignments(testCase: TestCase, compoundId: string): Promise<void> {
        let createParameterAssignmentTask: Promise<void> = Promise.resolve();
        let testParameters = this.testParameters;
        for(let i = 0; i < testParameters.length; i++) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                let currentTestParameter: TestParameter = testParameters[i] as TestParameter;
                let parameterAssignmentFactory: ParameterAssignmentFactory = new ParameterAssignmentFactory(this.dataService, currentTestParameter);
                return parameterAssignmentFactory.create(testCase, false, compoundId);
            }).then(() => Promise.resolve());
        }
        return createParameterAssignmentTask;
    }
}