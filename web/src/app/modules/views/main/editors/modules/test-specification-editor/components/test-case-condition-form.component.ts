import { Component, Input, ViewChild, ElementRef } from '@angular/core';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';
import { ParameterAssignment } from '../../../../../../../model/ParameterAssignment';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-case-condition-form',
    templateUrl: 'test-case-condition-form.component.html',
    styleUrls: ['test-case-condition-form.component.css']
})
export class TestCaseConditionForm extends SimpleInputFormBase {

    @Input()
    set paramAssignment(paramAssignment: ParameterAssignment) {
        this.modelElement = paramAssignment;
    }

    get fields(): string[] {
        return ['condition'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
    @ViewChild('textArea', { read: ElementRef }) textArea: ElementRef;

    autoGrow(): void {
        const textArea = this.textArea.nativeElement;
        textArea.style.overflow = 'hidden';
        textArea.style.height = '0px';
        textArea.style.height = 2 + textArea.scrollHeight + 'px';
    }
}
