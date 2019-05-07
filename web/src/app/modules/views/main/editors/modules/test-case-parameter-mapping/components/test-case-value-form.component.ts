import { Component, Input } from '@angular/core';
import { ParameterAssignment } from '../../../../../../../model/ParameterAssignment';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-case-value-form',
    templateUrl: 'test-case-value-form.component.html',
    styleUrls: ['test-case-value-form.component.css']
})
export class TestCaseValueForm extends SimpleInputFormBase {

    @Input()
    set paramAssignment(paramAssignment: ParameterAssignment) {
        this.modelElement = paramAssignment;
    }

    get fields(): string[] {
        return ['value'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
}
