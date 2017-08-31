import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TestCaseRow } from './test-case-row.component';
import { NgModule } from '@angular/core';

import { CoreModule } from '../core/core.module';
import { SpecmateFormsModule } from '../forms/specmate-forms.module';
import { TestSpecificationEditor } from './test-specification-editor.component';
import { TestParameterForm } from './test-parameter-form.component';
import { TestCaseConditionForm } from './test-case-condition-form.component';
import { TestCaseValueForm } from './test-case-value-form.component';
import { TestCaseNameForm } from './test-case-name-form.component';
import { TestProcedureEditor } from './test-procedure-editor.component';
import { TestCaseParameterMapping } from './test-case-parameter-mapping.component';
import { TestStepRow } from './test-step-row.component';
import { DndModule } from "ng2-dnd";

@NgModule({
    imports: [
        CoreModule,
        FormsModule,
        ReactiveFormsModule,
        SpecmateFormsModule,
        DndModule
    ],
    declarations: [
        TestSpecificationEditor,
        TestCaseRow,
        TestParameterForm,
        TestCaseConditionForm,
        TestCaseValueForm,
        TestCaseNameForm,
        TestProcedureEditor,
        TestCaseParameterMapping,
        TestStepRow
    ],
    providers: [],
    exports: [],
})
export class TestsModule { }
