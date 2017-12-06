import { NgModule } from "@angular/core";
import { ExportToALMButton } from "./components/export-to-alm-button.component";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ExportToALMButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ExportToALMButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ExportToALMButtonModule { }