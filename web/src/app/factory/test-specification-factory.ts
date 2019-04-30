import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { TestCase } from '../model/TestCase';
import { TestSpecification } from '../model/TestSpecification';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ElementFactoryBase } from './element-factory-base';
import { TestCaseFactory } from './test-case-factory';

export class TestSpecificationFactory extends ElementFactoryBase<TestSpecification> {

    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<TestSpecification> {
        compoundId = compoundId || Id.uuid;
        let testSpec = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([parent.url, testSpec.id]);
        testSpec.name = name || Config.TESTSPEC_NAME + ' ' + ElementFactoryBase.getDateStr();
        testSpec.description = Config.TESTSPEC_DESCRIPTION;

        return this.dataService.createElement(testSpec, true, compoundId)
            .then(() => this.createTestCase(testSpec))
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => testSpec);
    }

    private createTestCase(testSpec: TestSpecification): Promise<TestCase> {
        let factory: TestCaseFactory = new TestCaseFactory(this.dataService, false);
        return factory.create(testSpec, false);
    }
}
