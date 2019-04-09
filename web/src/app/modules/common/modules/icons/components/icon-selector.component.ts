import { Component, Input, OnInit } from '@angular/core';
import { CEGModel } from '../../../../../model/CEGModel';
import { Folder } from '../../../../../model/Folder';
import { IContainer } from '../../../../../model/IContainer';
import { Process } from '../../../../../model/Process';
import { Requirement } from '../../../../../model/Requirement';
import { TestProcedure } from '../../../../../model/TestProcedure';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Type } from '../../../../../util/type';
import { Url } from '../../../../../util/url';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    selector: 'icon-selector',
    moduleId: module.id.toString(),
    templateUrl: 'icon-selector.component.html'
})
export class IconSelector implements OnInit {

    @Input()
    model: IContainer;

    @Input()
    expanded: boolean;

    private parent: IContainer;

    get isFolder() {
        return Type.is(this.model, Folder);
    }

    get isCEGModel() {
        return Type.is(this.model, CEGModel);
    }

    get isNonRegressionRequirement() {
        return Type.is(this.model, Requirement) && !(<Requirement>this.model).isRegressionRequirement;
    }

    get isRegressionRequirement() {
        return Type.is(this.model, Requirement) && (<Requirement>this.model).isRegressionRequirement;
    }

    get isTestSpecification() {
        return Type.is(this.model, TestSpecification);
    }

    get isProcess() {
        return Type.is(this.model, Process);
    }

    get isManualTestSpecification(): boolean {
         return this.isTestSpecification && !this.isElementChildOfModel();
    }

    get isGeneratedTestSpecification(): boolean {
        return this.isTestSpecification && this.isElementChildOfModel();
    }

    get isTestProcedure() {
        return Type.is(this.model, TestProcedure);
    }

    private isElementChildOfModel(): boolean {
         return this.parent && (Type.is(this.parent, CEGModel) || Type.is(this.parent, Process));
    }

    constructor(private dataService: SpecmateDataService) { }

    public async ngOnInit(): Promise<void> {
        const parentUrl = Url.parent(this.model.url);
        if (parentUrl !== undefined) {
            this.parent = await this.dataService.readElement(parentUrl);
        }
    }
}
