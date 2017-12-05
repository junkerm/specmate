import { ElementFactoryBase } from "./element-factory-base";
import { TestSpecification } from "../model/TestSpecification";
import { SpecmateDataService } from "../services/data/specmate-data.service";
import { Id } from "../util/Id";
import { Url } from "../util/Url";
import { IContainer } from "../model/IContainer";
import { Config } from "../config/config";
import { TestCaseFactory } from "./test-case-factory";
import { TestCase } from "../model/TestCase";

export class TestSpecificationFactory extends ElementFactoryBase<TestSpecification> {
    
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<TestSpecification> {
        compoundId = compoundId || Id.uuid;
        var testSpec = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([parent.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        return this.dataService.createElement(testSpec, true, compoundId)
            .then(() => this.createTestCase(testSpec))
            .then(() => commit ? this.dataService.commit('Create') : Promise.resolve())
            .then(() => testSpec);
    }

    private createTestCase(testSpec: TestSpecification): Promise<TestCase> {
        let factory: TestCaseFactory = new TestCaseFactory(this.dataService, false);
        return factory.create(testSpec, false);
    }
}