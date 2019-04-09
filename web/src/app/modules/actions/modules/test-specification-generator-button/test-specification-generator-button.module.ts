import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { TestSpecificationGeneratorButton } from './components/test-specification-generator-button.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TestSpecificationGeneratorButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TestSpecificationGeneratorButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TestSpecificationGeneratorButtonModule { }
