import { ElementFactoryBase } from "./element-factory-base";
import { TestParameter } from "../model/TestParameter";
import { IContainer } from "../model/IContainer";
import { Id } from "../util/Id";
import { Url } from "../util/Url";
import { Config } from "../config/config";
import { TestCase } from "../model/TestCase";
import { ParameterAssignmentFactory } from "./parameter-assignment-factory";

export abstract class TestParameterFactory extends ElementFactoryBase<TestParameter> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<TestParameter> {
        compoundId = compoundId || Id.uuid;
        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let parameter: TestParameter = new TestParameter();
        parameter.name = Config.TESTPARAMETER_NAME;
        parameter.id = id;
        parameter.url = url;
        parameter.type = this.parameterType;
        parameter.assignments = [];
        
        return this.dataService.createElement(parameter, true, compoundId)
            .then(() => this.loadContents(parent))
            .then(() => this.createParameterAssignments(parameter, compoundId))
            .then(() => commit ? this.dataService.commit('Create') : Promise.resolve())
            .then(() => parameter);
    }

    private get testCases(): IContainer[] {
        return this.getContentsOfType(TestCase);
    }
    
    private createParameterAssignments(testParameter: TestParameter, compoundId: string): Promise<void> {
        let createParameterAssignmentTask: Promise<void> = Promise.resolve();
        let testCases = this.testCases;
        for(let i = 0; i < testCases.length; i++) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                let currentTestCase: TestCase = testCases[i] as TestCase;
                let parameterAssignmentFactory: ParameterAssignmentFactory = new ParameterAssignmentFactory(this.dataService, testParameter);
                return parameterAssignmentFactory.create(currentTestCase, false, compoundId);
            }).then(() => Promise.resolve());
        }
        return createParameterAssignmentTask;
    }

    protected abstract get parameterType(): string;
}