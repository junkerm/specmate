import { NgModule } from "@angular/core";
import { PageNotFound } from "./components/page-not-found.component";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [
    // MODULE IMPORTS
    NgbModule
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