import { ElementFactoryBase } from "./element-factory-base";
import { ParameterAssignment } from "../model/ParameterAssignment";
import { IContainer } from "../model/IContainer";
import { Id } from "../util/Id";
import { Proxy } from "../model/support/proxy";
import { Config } from "../config/config";
import { Url } from "../util/Url";
import { SpecmateDataService } from "../services/data/specmate-data.service";
import { TestParameter } from "../model/TestParameter";

export class ParameterAssignmentFactory extends ElementFactoryBase<ParameterAssignment> {

    constructor(dataService: SpecmateDataService, private testParameter: TestParameter) {
        super(dataService);
    }

    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<ParameterAssignment> {
        compoundId = compoundId || Id.uuid;
        let parameterAssignment: ParameterAssignment = new ParameterAssignment();
        let id: string = Id.uuid;
        let paramProxy: Proxy = new Proxy();
        paramProxy.url = this.testParameter.url;
        parameterAssignment.parameter = paramProxy;
        parameterAssignment.condition = Config.TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION;
        parameterAssignment.value = Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE;
        parameterAssignment.name = Config.TESTPARAMETERASSIGNMENT_NAME;
        parameterAssignment.id = id;
        parameterAssignment.url = Url.build([parent.url, id]);
        let assignmentProxy = new Proxy();
        assignmentProxy.url = parameterAssignment.url;
        this.testParameter.assignments.push(assignmentProxy);
        
        return this.dataService.createElement(parameterAssignment, true, compoundId)
            .then(() => commit ? this.dataService.commit('Create') : Promise.resolve())
            .then(() => parameterAssignment);
    }
    
}