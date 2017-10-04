import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/Type';
import { Sort } from '../../util/Sort';
import { IContainer } from '../../model/IContainer';
import { EditorCommonControlService } from '../../services/common-controls/editor-common-control.service';
import { Config } from '../../config/config';
import { Url } from '../../util/Url';
import { Id } from '../../util/Id';
import { TestSpecification } from '../../model/TestSpecification';
import { Requirement } from '../../model/Requirement';
import { IContentElement } from '../../model/IContentElement';
import { CEGModel } from '../../model/CEGModel';
import { NavigatorService } from '../../services/navigation/navigator.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationModal } from '../../services/notification/confirmation-modal.service';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { SpecmateViewBase } from '../core/views/specmate-view-base';
import { Process } from '../../model/Process';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessEnd } from '../../model/ProcessEnd';
import { IModelNode } from '../../model/IModelNode';
import { ProcessStep } from '../../model/ProcessStep';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessConnection } from '../../model/ProcessConnection';

export abstract class TestSpecificationGenerator extends SpecmateViewBase {
    
    protected requirementContents: IContentElement[];
    protected requirement: Requirement;
    protected allTestSpecifications: TestSpecification[];

    private canGenerateTestSpecMap: { [url: string]: boolean } = {};
   
    constructor(dataService: SpecmateDataService, modal: ConfirmationModal, route: ActivatedRoute, navigator: NavigatorService, editorCommonControlService: EditorCommonControlService) {
        super(dataService, navigator, route, modal, editorCommonControlService)
    }
    
    protected onElementResolved(element: IContainer): void {
        this.resolveRequirement(element).then((requirement: Requirement) => this.init(requirement));
    }

    private init(requirement: Requirement): void {
        this.requirement = requirement;
        this.dataService.readContents(this.requirement.url).then((
            contents: IContainer[]) => {
            this.requirementContents = contents;
            for (let i = 0; i < this.requirementContents.length; i++) {
                let currentElement: IContainer = this.requirementContents[i];
                if(Type.is(currentElement, CEGModel) || Type.is(currentElement, Process)) {
                    this.initCanCreateTestSpec(currentElement);
                }
            }
        });
        this.readAllTestSpecifications();
    }

    protected abstract resolveRequirement(element: IContainer): Promise<Requirement>;

    private initCanCreateTestSpec(currentElement: IContainer): void {
        this.dataService.readContents(currentElement.url).then((contents: IContainer[]) => {
            this.doCheckCanCreateTestSpec(currentElement, contents);
        });
    }

    private static isNode(element: IContainer): boolean {
        return (Type.is(element, CEGNode));
    }

    private static hasNodes(contents: IContainer[]): boolean {
        return contents.filter((element: IContainer) => TestSpecificationGenerator.isNode(element)).length > 0;
    }

    protected doCheckCanCreateTestSpec(currentElement: IContainer, contents: IContainer[]): void {
        if(Type.is(currentElement, CEGModel)) {
            let hasSingleNode: boolean = this.checkForSingleNodes(contents);
            let hasDuplicateIOVariable: boolean = this.checkForDuplicateIOVariable(contents);
            this.canGenerateTestSpecMap[currentElement.url] = !hasSingleNode && !hasDuplicateIOVariable && TestSpecificationGenerator.hasNodes(contents);
        }
        else if(Type.is(currentElement, Process)) {
            let hasSingleStartNode: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessStart)).length == 1;
            let hasEndNodes: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessEnd)).length > 0;
            let processNodes: IModelNode[] = contents.filter((element: IContainer) => Type.is(element, ProcessEnd) || Type.is(element, ProcessStart) || Type.is(element, ProcessDecision) || Type.is(element, ProcessStep)) as IModelNode[];
            let hasNodeWithoutIncomingConnections: boolean = processNodes.find((element: IModelNode) => (!element.incomingConnections || (element.incomingConnections && element.incomingConnections.length == 0)) && !Type.is(element, ProcessStart)) !== undefined;
            let hasNodeWithoutOutgoingConnections: boolean = processNodes.find((element: IModelNode) => (!element.outgoingConnections || (element.outgoingConnections && element.outgoingConnections.length == 0)) && !Type.is(element, ProcessEnd)) !== undefined;
            

            let processConnections: ProcessConnection[] = contents.filter((element: IContainer) => Type.is(element, ProcessConnection)) as ProcessConnection[];
            let decisionNodes: ProcessDecision[] = processNodes.filter((element: IModelNode) => Type.is(element, ProcessDecision)) as ProcessDecision[];
            let decisionConnections: ProcessConnection[] = processConnections.filter((connection: ProcessConnection) => decisionNodes.find((node: ProcessDecision) => node.url === connection.source.url) !== undefined);
            let hasMissingConditions: boolean = decisionConnections.find((connection: ProcessConnection) => connection.condition === undefined || connection.condition === null || connection.condition === '') !== undefined;

            this.canGenerateTestSpecMap[currentElement.url] = hasSingleStartNode && hasEndNodes && !hasNodeWithoutIncomingConnections && !hasNodeWithoutOutgoingConnections && !hasMissingConditions;
        }
    }

    protected checkForSingleNodes(contents: IContainer[]): boolean {
        return contents.some((element: IContainer) => {
            let isNode: boolean = TestSpecificationGenerator.isNode(element);
            if (!isNode) {
                return false;
            }
            let node: CEGNode = element as CEGNode;
            let hasIncomingConnections: boolean = node.incomingConnections && node.incomingConnections.length > 0;
            let hasOutgoingConnections: boolean = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
    }

    protected checkForDuplicateIOVariable(contents: IContainer[]){
         let variableMap: { [variable: string]: string } = {};
         for(var content of contents){
             if(!TestSpecificationGenerator.isNode(content)){
                 continue;
             }
            let node: CEGNode = content as CEGNode;
            let type:string;
            if(!node.incomingConnections || node.incomingConnections.length<=0){
                type="input";
            }
            else if(!node.outgoingConnections || node.outgoingConnections.length<=0){
                type="output";
            } else {
                type="intermediate";
            }
            let existing:string = variableMap[node.variable];
            if(existing){
                if(existing==="input" && type==="output" || existing==="output" && type==="input"){
                    return true;
                }
            } else {
                variableMap[node.variable]=type;
            }
         }
         return false;
    }

    protected readAllTestSpecifications(){
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification.className }).then(
            (testSpecifications: TestSpecification[]) => this.allTestSpecifications = Sort.sortArray(testSpecifications));
    }

    protected canCreateTestSpecification(ceg: CEGModel): boolean {
        return this.canGenerateTestSpecMap[ceg.url];
    }

    protected generateTestSpecification(ceg: CEGModel): void {
        if (!this.requirementContents) {
            return;
        }
        if(!this.canCreateTestSpecification(ceg)) {
            return;
        }
        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([ceg.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(() => this.dataService.createElement(testSpec, true, Id.uuid))
            .then(() => this.dataService.commit('Create'))
            .then(() => this.dataService.performOperations(testSpec.url, 'generateTests'))
            .then(() => this.navigator.navigate(testSpec))
            .catch(() => {});
    }

    protected createTestSpecification(): void {
        if (!this.requirementContents) {
            return;
        }

        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([this.requirement.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.dataService.createElement(testSpec, true, Id.uuid)
            .then(() => this.dataService.commit('Create'))
            .then(() => this.navigator.navigate(testSpec));
    }
}