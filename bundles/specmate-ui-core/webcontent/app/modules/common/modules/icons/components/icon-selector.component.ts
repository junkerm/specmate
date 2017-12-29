import { Component, Input } from '@angular/core';
import { ISpecmateModelObject } from '../../../../../model/ISpecmateModelObject';
import { Folder } from '../../../../../model/Folder';
import { CEGModel } from '../../../../../model/CEGModel';
import { Process } from '../../../../../model/Process';
import { Requirement } from '../../../../../model/Requirement';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Type } from '../../../../../util/type';

@Component({
    selector: 'icon-selector',
    moduleId: module.id.toString(),
    templateUrl: 'icon-selector.component.html'
})
export class IconSelector {

    @Input()
    model: ISpecmateModelObject;

    @Input()
    expanded: boolean;

    @Input()
    parent: ISpecmateModelObject;

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

    public get isManualTestSpecification(): boolean {
         return this.isTestSpecification && !this.isElementChildOfModel();
    }

    public get isGeneratedTestSpecification(): boolean {
        return this.isTestSpecification && this.isElementChildOfModel();
    }

    private isElementChildOfModel(): boolean {
         return this.parent && (Type.is(this.parent, CEGModel) || Type.is(this.parent, Process));
    }

}
