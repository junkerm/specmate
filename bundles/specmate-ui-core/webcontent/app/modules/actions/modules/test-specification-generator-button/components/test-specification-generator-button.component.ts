import { Component, Input } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { CEGModel } from '../../../../../model/CEGModel';
import { Process } from '../../../../../model/Process';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Id } from '../../../../../util/id';
import { Url } from '../../../../../util/url';
import { Config } from '../../../../../config/config';
import { Type } from '../../../../../util/type';
import { CEGNode } from '../../../../../model/CEGNode';
import { ProcessStart } from '../../../../../model/ProcessStart';
import { ProcessEnd } from '../../../../../model/ProcessEnd';
import { IModelNode } from '../../../../../model/IModelNode';
import { ProcessStep } from '../../../../../model/ProcessStep';
import { ProcessConnection } from '../../../../../model/ProcessConnection';
import { ProcessDecision } from '../../../../../model/ProcessDecision';

@Component({
    moduleId: module.id,
    selector: 'test-specification-generator-button',
    templateUrl: 'test-specification-generator-button.component.html',
    styleUrls: ['test-specification-generator-button.component.css']
})

export class TestSpecificationGeneratorButton {

    private errorMessageTestSpecMap: { [url: string]: string[] } = {};

    private contents: IContainer[];

    private _model: CEGModel | Process;

    private static isCEGNode(element: IContainer): boolean {
        return (Type.is(element, CEGNode));
    }

    public get model(): CEGModel | Process {
        return this._model;
    }

    @Input()
    public set model(model: CEGModel | Process) {
        if (!model) {
            return;
        }
        this._model = model;
        this.dataService.readContents(model.url).then((contents: IContainer[]) => {
            this.contents = contents;
            this.doCheckCanCreateTestSpec();
        });
    }

    ngDoCheck() {
        this.doCheckCanCreateTestSpec();
    }

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private navigator: NavigatorService) { }

    public generate(): void {
        this.generateTestSpecification(this.model);
    }

    public get enabled(): boolean {
        return this.canCreateTestSpecification(this.model);
    }

    public get errors(): string[] {
        return this.getErrors(this.model);
    }

    public generateTestSpecification(model: CEGModel | Process): void {
        if (!this.canCreateTestSpecification(model)) {
            return;
        }
        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([model.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(() => this.dataService.createElement(testSpec, true, Id.uuid))
            .then(() => this.dataService.commit('Save'))
            .then(() => this.dataService.performOperations(testSpec.url, 'generateTests'))
            .then(() => this.dataService.readContents(testSpec.url))
            .then(() => this.navigator.navigate(testSpec))
            .catch(() => {});
    }

    public canCreateTestSpecification(model: CEGModel | Process): boolean {
        if (!model) {
            return false;
        }
        return this.errorMessageTestSpecMap[model.url] === undefined || this.errorMessageTestSpecMap[model.url].length === 0;
    }

    public getErrors(model: CEGModel | Process): string[] {
        if (!model) {
            return undefined;
        }
        return this.errorMessageTestSpecMap[model.url];
    }

    private addErrorMessage(element: IContainer, message: string): void {
        let url: string = element.url;
        if (!this.errorMessageTestSpecMap[url]) {
            this.errorMessageTestSpecMap[url] = [];
        }
        this.errorMessageTestSpecMap[url].push(message);
    }

    // TODO: Move to separate class (VALIDATORS)
    private doCheckCanCreateTestSpec(): void {
        if (!this._model || !this.contents) {
            return;
        }
        this.errorMessageTestSpecMap[this.model.url] = [];
        if (Type.is(this.model, CEGModel)) {
            if (this.checkForSingleNodes(this.contents)) {
                this.addErrorMessage(this.model, Config.ERROR_UNCONNECTED_NODE);
            }
            if (this.checkForDuplicateIOVariable(this.contents)) {
                this.addErrorMessage(this.model, Config.ERROR_DUPLICATE_IO_VARIABLE);
            }
            if (this.checkForDuplicateNodes(this.contents)) {
                this.addErrorMessage(this.model, Config.ERROR_DUPLICATE_NODE);
            }
            if (this.contents.findIndex((element: IContainer) => Type.is(element, CEGNode)) === -1) {
                this.addErrorMessage(this.model, Config.ERROR_EMPTY_MODEL);
            }
        } else if (Type.is(this.model, Process)) {
            let hasSingleStartNode: boolean = this.contents.filter((element: IContainer) => Type.is(element, ProcessStart)).length === 1;
            if (!hasSingleStartNode) {
                this.addErrorMessage(this.model, Config.ERROR_NOT_ONE_START_NODE);
            }

            let hasEndNodes: boolean = this.contents.filter((element: IContainer) => Type.is(element, ProcessEnd)).length > 0;
            if (!hasEndNodes) {
                this.addErrorMessage(this.model, Config.ERROR_NO_END_NODE);
            }
            let processNodes: IModelNode[] =
                this.contents.filter((element: IContainer) =>
                    Type.is(element, ProcessEnd) ||
                    Type.is(element, ProcessStart) ||
                    Type.is(element, ProcessDecision) ||
                    Type.is(element, ProcessStep)) as IModelNode[];
            let hasNodeWithoutIncomingConnections: boolean =
                processNodes.find((element: IModelNode) =>
                    (!element.incomingConnections ||
                        (element.incomingConnections && element.incomingConnections.length === 0)) &&
                        !Type.is(element, ProcessStart)) !== undefined;
            if (hasNodeWithoutIncomingConnections) {
                this.addErrorMessage(this.model, Config.ERROR_NODE_WITHOUT_INCOMING);
            }
            let hasNodeWithoutOutgoingConnections: boolean =
                processNodes.find((element: IModelNode) =>
                    (!element.outgoingConnections ||
                        (element.outgoingConnections && element.outgoingConnections.length === 0)) &&
                        !Type.is(element, ProcessEnd)) !== undefined;
            if (hasNodeWithoutOutgoingConnections) {
                this.addErrorMessage(this.model, Config.ERROR_NODE_WITHOUT_OUTGOING);
            }

            let processSteps: IModelNode[] = processNodes.filter((element: IModelNode) => Type.is(element, ProcessStep));
            if (processSteps.length === 0) {
                this.addErrorMessage(this.model, Config.ERROR_NO_STEPS);
            }

            let processConnections: ProcessConnection[] =
                this.contents.filter((element: IContainer) => Type.is(element, ProcessConnection)) as ProcessConnection[];
            let decisionNodes: ProcessDecision[] =
                processNodes.filter((element: IModelNode) => Type.is(element, ProcessDecision)) as ProcessDecision[];
            let decisionConnections: ProcessConnection[] =
                processConnections.filter((connection: ProcessConnection) =>
                    decisionNodes.find((node: ProcessDecision) => node.url === connection.source.url) !== undefined);
            let hasMissingConditions: boolean =
                decisionConnections.find((connection: ProcessConnection) =>
                    connection.condition === undefined || connection.condition === null || connection.condition === '') !== undefined;
            if (hasMissingConditions) {
                this.addErrorMessage(this.model, Config.ERROR_MISSING_CONDITION);
            }
        }
    }

    protected checkForSingleNodes(contents: IContainer[]): boolean {
        return contents.some((element: IContainer) => {
            let isNode: boolean = TestSpecificationGeneratorButton.isCEGNode(element);
            if (!isNode) {
                return false;
            }
            let node: CEGNode = element as CEGNode;
            let hasIncomingConnections: boolean = node.incomingConnections && node.incomingConnections.length > 0;
            let hasOutgoingConnections: boolean = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
    }

    protected checkForDuplicateNodes(contents: IContainer[]): boolean {
        let nodes: CEGNode[] =
            contents.filter((element: IContainer) => Type.is(element, CEGNode)).map((element: IContainer) => element as CEGNode);
        for (let i = 0; i < nodes.length; i++) {
            let currentNode: CEGNode = nodes[i];
            let isDuplicate: boolean =
                nodes.some((otherNode: CEGNode) =>
                    otherNode.variable === currentNode.variable &&
                    otherNode.condition === currentNode.condition &&
                    otherNode !== currentNode);
            if (isDuplicate) {
                return true;
            }
        }
        return false;
    }

    protected checkForDuplicateIOVariable(contents: IContainer[]): boolean {
         let variableMap: { [variable: string]: string } = {};
         for (let content of contents) {
            if (!TestSpecificationGeneratorButton.isCEGNode(content)) {
                continue;
            }
            let node: CEGNode = content as CEGNode;
            let type: string;
            if (!node.incomingConnections || node.incomingConnections.length <= 0) {
                type = 'input';
            } else if (!node.outgoingConnections || node.outgoingConnections.length <= 0) {
                type = 'output';
            } else {
                type = 'intermediate';
            }
            let existing: string = variableMap[node.variable];
            if (existing) {
                if (existing === 'input' && type === 'output' || existing === 'output' && type === 'input') {
                    return true;
                }
            } else {
                variableMap[node.variable] = type;
            }
        }
        return false;
    }
}
