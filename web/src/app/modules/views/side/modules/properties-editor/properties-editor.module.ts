import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { IconsModule } from '../../../../common/modules/icons/icons.module';
import { GenericFormModule } from '../../../../forms/modules/generic-form/generic-form.module';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { PropertiesEditor } from './components/properties-editor.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    GenericFormModule,
    NavigatorModule,
    NgbModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    SpecmateSharedModule,
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
