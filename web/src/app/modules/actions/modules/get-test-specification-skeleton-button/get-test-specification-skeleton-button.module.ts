import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { GetTestSpecificationSkeletonButton } from './components/get-test-specification-skeleton-button.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    GetTestSpecificationSkeletonButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    GetTestSpecificationSkeletonButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class GetTestSpecificationSkeletonButtonModule { }
