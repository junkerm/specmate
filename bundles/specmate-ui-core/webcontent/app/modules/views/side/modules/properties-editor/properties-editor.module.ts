import {NavigatorModule} from '../../../../navigation/modules/navigator/navigator.module';
import { NgModule } from '@angular/core';
import { PropertiesEditor } from './components/properties-editor.component';
import { GenericFormModule } from '../../../../forms/modules/generic-form/generic-form.module';
import { BrowserModule } from '@angular/platform-browser';
import { TracingLinks } from './components/tracing-links.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    GenericFormModule,
    NavigatorModule,
    NgbModule.forRoot(),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    PropertiesEditor,
    TracingLinks
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
