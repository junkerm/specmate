import { NgModule } from '@angular/core';
import { GetTestSpecificationSkeletonButton } from './components/get-test-specification-skeleton-button.component';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule
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
