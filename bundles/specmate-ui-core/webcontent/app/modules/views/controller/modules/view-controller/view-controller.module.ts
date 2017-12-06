import { NgModule } from "@angular/core";
import { ViewControllerService } from "./services/view-controller.service";

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
    ViewControllerService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ViewControllerModule { }