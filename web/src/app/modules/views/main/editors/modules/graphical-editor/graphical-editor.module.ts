import { NgModule } from '@angular/core';
import { GraphicalEditor } from './components/graphical-editor.component';
import { MaximizeButtonModule } from '../maximize-button/maximize-button.module';
import { BrowserModule } from '@angular/platform-browser';
import { ToolPalletteModule } from '../tool-pallette/tool-pallette.module';
import { CEGGraphicalConnection } from './components/ceg/ceg-graphical-connection.component';
import { CEGGraphicalNode } from './components/ceg/ceg-graphical-node.component';
import { ProcessGraphicalConnection } from './components/process/process-graphical-connection.component';
import { ProcessStartGraphicalNode } from './components/process/process-start-graphical-node.component';
import { ProcessEndGraphicalNode } from './components/process/process-end-graphical-node.component';
import { ProcessStepGraphicalNode } from './components/process/process-step-graphical-node.component';
import { ProcessDecisionGraphicalNode } from './components/process/process-decision-graphical-node.component';
import { CEGGraphicalArc } from './components/ceg/ceg-graphical-arc.component';
import { ConnectionLine } from './components/common/connection-line.component';
import { ArrowTip } from './components/common/arrow-tip.component';
import { TruncatedText } from './components/common/truncated-text.component';
import { TranslateModule } from '@ngx-translate/core';
import { GenericGraphicalNode } from './components/common/generic-graphical-node.component';
import { GenericGraphicalConnection } from './components/common/generic-graphical-connection.component';
import {NgxPrintModule} from 'ngx-print';

@NgModule({
  imports: [
    // MODULE IMPORTS
    NgxPrintModule,
    MaximizeButtonModule,
    ToolPalletteModule,
    BrowserModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    GraphicalEditor,
    GenericGraphicalNode,
    GenericGraphicalConnection,
    CEGGraphicalConnection,
    CEGGraphicalNode,
    CEGGraphicalArc,
    ProcessGraphicalConnection,
    ProcessStartGraphicalNode,
    ProcessEndGraphicalNode,
    ProcessStepGraphicalNode,
    ProcessDecisionGraphicalNode,
    ConnectionLine,
    ArrowTip,
    TruncatedText
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    GraphicalEditor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class GraphicalEditorModule { }
