import { NgModule } from "@angular/core";
import { GraphicalEditor } from "./graphical-editor.component";
import { GraphicalElementDetails } from "./graphical-element-details.component";
import { CEGGraphicalNode } from "./elements/ceg/ceg-graphical-node.component";
import { CEGGraphicalArc } from "./elements/ceg/ceg-graphical-arc.component";
import { CEGGraphicalConnection } from "./elements/ceg/ceg-graphical-connection.component";
import { BrowserModule } from "@angular/platform-browser";
import { PipeModule } from "../../../pipes/pipe.module";
import { SpecmateFormsModule } from "../../forms/specmate-forms.module";
import { ProcessStepGraphicalNode } from "./elements/process/process-step-graphical-node.component";
import { ProcessGraphicalConnection } from "./elements/process/process-graphical-connection.component";

@NgModule({
    imports: [
        BrowserModule,
        PipeModule,
        SpecmateFormsModule
    ],
    declarations: [
        GraphicalEditor,
        GraphicalElementDetails,
        CEGGraphicalNode,
        CEGGraphicalArc,
        CEGGraphicalConnection,
        ProcessStepGraphicalNode,
        ProcessGraphicalConnection
    ],
    providers: [],
    bootstrap: [],
    exports: [
        GraphicalEditor
    ],
  entryComponents: []
})

export class GraphicalModule { }