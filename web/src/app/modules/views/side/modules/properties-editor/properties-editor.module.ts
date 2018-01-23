import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { NgModule } from '@angular/core';
import { PropertiesEditor } from './components/properties-editor.component';
import { GenericFormModule } from '../../../../forms/modules/generic-form/generic-form.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IconsModule } from '../../../../common/modules/icons/icons.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    GenericFormModule,
    NavigatorModule,
    NgbModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    IconsModule
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
