import { Component } from "@angular/core";
import { NavigatorService } from "../../../services/navigation/navigator.service";
import { IContainer } from "../../../model/IContainer";
import { Type } from "../../../util/Type";
import { Requirement } from "../../../model/Requirement";
import { TestSpecification } from "../../../model/TestSpecification";
import { CEGModel } from "../../../model/CEGModel";
import { Process } from "../../../model/Process";
import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { Url } from "../../../util/Url";
import { IConnection } from "../../../model/IConnection";
import { Sort } from "../../../util/Sort";
import { TestProcedure } from "../../../model/TestProcedure";
import { AdditionalInformationService } from "../../../services/additional-information/additional-information.service";


@Component({
    moduleId: module.id,
    selector: 'links-actions',
    templateUrl: 'links-actions.component.html',
    styleUrls: ['links-actions.component.css']
})
export class LinksActions {

    public _requirement: Requirement;
    public _model: IContainer;
    public _contents: IContainer[];
    public _testSpecifications: TestSpecification[];

    constructor(private additionalInformationService: AdditionalInformationService) { }

    public get model(): IContainer {
        return this.additionalInformationService.model;
    }

    public get requirement(): Requirement {
        return this.additionalInformationService.requirement;
    }

    public get testSpecification(): TestSpecification {
        return this.additionalInformationService.testSpecification;
    }

    public get testSpecifications(): TestSpecification[] {
        return this.additionalInformationService.testSpecifications;
    }

    public get canHaveTestSpecifications(): boolean {
        return this.additionalInformationService.canHaveTestSpecifications;
    }

    public get canGenerateTestSpecifications(): boolean {
        return this.additionalInformationService.canGenerateTestSpecifications;
    }

    public get canAddTestSpecifications(): boolean {
        return this.additionalInformationService.canAddTestSpecifications;
    }

    public get canExportToALM(): boolean {
        return this.additionalInformationService.canExportToALM;
    }
}
