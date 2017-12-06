import { NgModule } from "@angular/core";
import { PropertiesEditor } from "./components/properties-editor.component";
import { GenericFormModule } from "../../../../forms/modules/generic-form/generic-form.module";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    GenericFormModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    PropertiesEditor
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    PropertiesEditor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class PropertiesEditorModule { }