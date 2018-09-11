import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { TestCaseParameterMapping } from './components/test-case-parameter-mapping.component';
import { TestCaseValueForm } from './components/test-case-value-form.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TestCaseParameterMapping,
    TestCaseValueForm
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TestCaseParameterMapping
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TestCaseParameterMappingModule { }
