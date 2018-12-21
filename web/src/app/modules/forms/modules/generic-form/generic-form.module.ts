import { NgModule } from '@angular/core';
import { GenericForm } from './components/generic-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormTextInput } from './components/form-text-input.component';
import { FormCheckboxInput } from './components/form-checkbox-input.component';
import { FormLongTextInput } from './components/form-long-text-input.component';
import { FormSingleSelectionInput } from './components/form-single-selection-input.component';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    FormsModule,
    SpecmateSharedModule,
    ReactiveFormsModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    GenericForm,
    FormTextInput,
    FormCheckboxInput,
    FormLongTextInput,
    FormSingleSelectionInput
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    GenericForm
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class GenericFormModule { }
