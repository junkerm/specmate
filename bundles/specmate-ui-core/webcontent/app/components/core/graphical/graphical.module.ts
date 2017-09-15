import { NgModule } from "@angular/core";
import { GraphicalEditor } from "./graphical-editor.component";
import { GraphicalElementDetails } from "./graphical-element-details.component";
import { CEGGraphicalNode } from "./elements/ceg/ceg-graphical-node.component";
import { CEGGraphicalArc } from "./elements/ceg/ceg-graphical-arc.component";
import { CEGGraphicalConnection } from "./elements/ceg/ceg-graphical-connection.component";
import { BrowserModule } from "@angular/platform-browser";
import { PipeModule } from "../../../pipes/pipe.module";
import { SpecmateFormsModule } from "../../forms/specmate-forms.module";

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
        GraphicalElementDetails
    ],
    providers: [],
    bootstrap: [],
    exports: [
        GraphicalEditor
    ],
  entryComponents: []
})

export class GraphicalModule { }