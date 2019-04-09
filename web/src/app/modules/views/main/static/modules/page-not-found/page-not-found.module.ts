import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
import { PageNotFound } from './components/page-not-found.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    NgbModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    PageNotFound
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    PageNotFound
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class PageNotFoundModule { }
