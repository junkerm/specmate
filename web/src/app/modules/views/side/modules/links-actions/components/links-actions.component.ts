import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { AdditionalInformationService } from '../services/additional-information.service';
import { Strings } from '../../../../../../util/strings';

@Component({
    moduleId: module.id.toString(),
    selector: 'links-actions',
    templateUrl: 'links-actions.component.html',
    styleUrls: ['links-actions.component.css']
})
export class LinksActions {

    public _requirement: Requirement;
    public _model: IContainer;
    public _contents: IContainer[];
    public _testSpecifications: TestSpecification[];
    public descriptionVisible: boolean;

    constructor(private additionalInformationService: AdditionalInformationService) { }

    public get element(): IContainer {
        return this.additionalInformationService.element;
    }

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

    public toggleDescription() {
        this.descriptionVisible = !this.descriptionVisible;
    }

    public get descriptionCollapsibleLabel(): string {
        if (this.requirement.description.length > 30 && !this.descriptionVisible) {
            return 'show more';
        }

        return '';
    }

    public get requirementDescription(): string {
        if (this.requirement.description.length > 30 && !this.descriptionVisible) {
            return Strings.truncate(this.requirement.description, 30);
        }

        return this.requirement.description;
    }
}
