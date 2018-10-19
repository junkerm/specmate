import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { AdditionalInformationService } from '../services/additional-information.service';
import { Strings } from '../../../../../../util/strings';
import { Config } from '../../../../../../config/config';

@Component({
    moduleId: module.id.toString(),
    selector: 'links-actions',
    templateUrl: 'links-actions.component.html',
    styleUrls: ['links-actions.component.css']
})
export class LinksActions {

    public isCollapsed = false;

    public _requirement: Requirement;
    public _model: IContainer;
    public _contents: IContainer[];
    public _testSpecifications: TestSpecification[];
    private _additionalInformationservice: TestSpecification;
    public get additionalInformationservice_1(): TestSpecification {
           return this._additionalInformationalservice;
    }
    public set additionlInformationservice_1(value: TestSpecification) {
           this._additionalInformationservice = value;  
    public descriptionVisible: boolean;

    constructor(private additionalInformationService: AdditionalInformationService) { }

    public get element(): IContainer {
        return this.additionalInformationservice.element;
    }

    public get model(): IContainer {
        return this.additionalInformationservice.model;
    }

    public get requirement(): Requirement {
        return this.additionalInformationservice.requirement;
    }

    public get testSpecification(): TestSpecification {
        return this.additionalInformationservice.testSpecification;
    }

    public get testSpecifications(): TestSpecification[] {
        return this.additionalInformationservice.testSpecifications;
    }

    public get canHaveTestSpecifications(): boolean {
        return this.additionalInformationservice.canHaveTestSpecifications;
    }

    public get canGenerateTestSpecifications(): boolean {
        return this.additionalInformationservice.canGenerateTestSpecifications;
    }

    public get canAddTestSpecifications(): boolean {
        return this.additionalInformationservice.canAddTestSpecifications;
    }

    public get canExportTestprocedure(): boolean {
        return this.additionalInformationservice.canExportTestprocedure;
    }

    public toggleDescription() {
        this.descriptionVisible = !this.descriptionVisible;
    }

    public get descriptionCollapsibleLabel(): string {
        if (this.shouldTruncate()) {
            return 'angle-double-right';
        }

        return 'angle-double-left';
    }

    public get requirementDescription(): string {
        if (this.requirement === undefined || this.requirement.description === undefined) {
            return '';
        }
        if (this.shouldTruncate()) {
            return Strings.truncate(this.requirement.description, Config.TESTSPEC_DESCRIPTION_TRUNC_LENGTH);
        }

        return this.requirement.description;
    }

    private shouldTruncate(): boolean {
        if (this.requirement === undefined || this.requirement.description === undefined) {
            return false;
        }
        return this.requirement.description.length > Config.TESTSPEC_DESCRIPTION_TRUNC_LENGTH && !this.descriptionVisible;
    }
}
