import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { GetTestProcedureSkeletonButton } from './components/get-test-procedure-skeleton-button.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    GetTestProcedureSkeletonButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    GetTestProcedureSkeletonButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class GetTestProcedureSkeletonButtonModule { }
