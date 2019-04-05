import { Component, Input, ViewChild, ElementRef } from '@angular/core';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';
import { TestCase } from '../../../../../../../model/TestCase';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-case-name-form',
    templateUrl: 'test-case-name-form.component.html',
    styleUrls: ['test-case-name-form.component.css']
})
export class TestCaseNameForm extends SimpleInputFormBase {

    @Input()
    set testCase(testCase: TestCase) {
        this.modelElement = testCase;
    }

    protected get fields(): string[] {
        return ['name'];
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
