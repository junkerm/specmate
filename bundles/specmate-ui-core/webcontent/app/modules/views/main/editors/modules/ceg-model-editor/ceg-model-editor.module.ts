import { NgModule } from "@angular/core";
import { CEGModelDetails } from "./components/ceg-model-details.component";
import { GraphicalEditorModule } from "../graphical-editor/graphical-editor.module";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
  imports: [
    // MODULE IMPORTS
    GraphicalEditorModule,
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    CEGModelDetails
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    CEGModelDetails
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class CEGModelEditorModule { }