import { OnInit, Component, ViewChild, ElementRef, Input } from '@angular/core';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';
import { TestCase } from '../../../../../../../model/TestCase';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-case-resizable-input',
    templateUrl: 'test-case-resizable-input.component.html',
    styleUrls: ['test-case-resizable-input.component.css']
})
export class TestCaseResizableInput extends SimpleInputFormBase implements OnInit  {
    @ViewChild('textArea', { read: ElementRef }) textArea: ElementRef;

    ngOnInit(): void {
        const textArea = this.textArea.nativeElement;
        textArea.style.overflow = 'hidden';
        textArea.style.height = '0px';
        textArea.style.height = 2 + textArea.scrollHeight + 'px';
    }
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

}
