import { ElementFactoryBase } from "./element-factory-base";
import { TestProcedure } from "../model/TestProcedure";
import { IContainer } from "../model/IContainer";
import { Id } from "../util/Id";
import { Url } from "../util/Url";
import { Config } from "../config/config";
import { TestStepFactory } from "./test-step-factory";
import { TestStep } from "../model/TestStep";

export class TestProcedureFactory extends ElementFactoryBase<TestProcedure> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<TestProcedure> {
        compoundId = compoundId || Id.uuid;
        let id = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let testProcedure: TestProcedure = new TestProcedure();
        testProcedure.name = Config.TESTPROCEDURE_NAME;
        testProcedure.description = Config.TESTPROCEDURE_DESCRIPTION;
        testProcedure.id = id;
        testProcedure.url = url;
        testProcedure.isRegressionTest = false;

        return this.dataService.createElement(testProcedure, true, compoundId)
            .then(() => this.createTestCase(testProcedure, compoundId))
            .then(() => commit ? this.dataService.commit("Create") : Promise.resolve())
            .then(() => testProcedure);
    }
    
    private createTestCase(testProcedure: TestProcedure, compoundId: string): Promise<TestStep> {
        let factory: TestStepFactory = new TestStepFactory(this.dataService);
        return factory.create(testProcedure, false, compoundId);
    }
}