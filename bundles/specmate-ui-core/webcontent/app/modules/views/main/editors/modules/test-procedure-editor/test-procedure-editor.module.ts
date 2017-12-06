import { NgModule } from "@angular/core";
import { TestProcedureEditor } from "./components/test-procedure-editor.component";
import { AngularSplitModule } from "angular-split";
import { MaximizeButtonModule } from "../maximize-button/maximize-button.module";
import { DragulaModule } from "ng2-dragula";
import { TestStepRow } from "./components/test-step-row.component";
import { BrowserModule } from "@angular/platform-browser";
import { TestCaseParameterMappingModule } from "../test-case-parameter-mapping/test-case-parameter-mapping.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    AngularSplitModule,
    MaximizeButtonModule,
    DragulaModule,
    TestCaseParameterMappingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TestProcedureEditor,
    TestStepRow
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TestProcedureEditor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TestProcedureEditorModule { }