import { ElementFactoryBase } from './element-factory-base';
import { TestStep } from '../model/TestStep';
import { IContainer } from '../model/IContainer';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { Config } from '../config/config';
import { Type } from '../util/type';

export class TestStepFactory extends ElementFactoryBase<TestStep> {
    public create(parent: IContainer, commit: boolean, compoundId?: string, name?: string): Promise<TestStep> {
        compoundId = compoundId || Id.uuid;
        let id = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let position: number = this.contents ? this.contents.length : 0;
        let testStep: TestStep = new TestStep();
        testStep.name = name || Config.TESTSTEP_NAME + ' ' + ElementFactoryBase.getDateStr();
        testStep.description = Config.TESTSTEP_ACTION;
        testStep.expectedOutcome = Config.TESTSTEP_EXPECTED_OUTCOME;
        testStep.id = id;
        testStep.url = url;
        testStep.position = position;
        testStep.referencedTestParameters = [];

        return this.dataService.readContents(parent.url)
            .then((contents: IContainer[]) =>
                testStep.position = contents.filter((element: IContainer) => Type.is(element, TestStep)).length)
            .then(() => this.dataService.createElement(testStep, true, compoundId))
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => testStep);
    }
}
