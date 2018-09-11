import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { DragulaModule } from 'ng2-dragula';
import { NavigatorModule } from '../../../../../navigation/modules/navigator/navigator.module';
import { MaximizeButtonModule } from '../maximize-button/maximize-button.module';
import { TestCaseConditionForm } from './components/test-case-condition-form.component';
import { TestCaseNameForm } from './components/test-case-name-form.component';
import { TestCaseRow } from './components/test-case-row.component';
import { TestParameterForm } from './components/test-parameter-form.component';
import { TestSpecificationEditor } from './components/test-specification-editor.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    MaximizeButtonModule,
    BrowserModule,
    DragulaModule,
    FormsModule,
    ReactiveFormsModule,
    NavigatorModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TestSpecificationEditor,
    TestCaseConditionForm,
    TestCaseNameForm,
    TestCaseRow,
    TestParameterForm
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TestSpecificationEditor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TestSpecificationEditorModule { }
