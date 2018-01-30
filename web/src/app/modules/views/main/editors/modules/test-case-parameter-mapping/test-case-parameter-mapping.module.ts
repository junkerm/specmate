import { NgModule } from '@angular/core';
import { TestCaseParameterMapping } from './components/test-case-parameter-mapping.component';
import { TestCaseValueForm } from './components/test-case-value-form.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

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
