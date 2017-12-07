import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AngularSplitModule } from 'angular-split';
import { SpecmateComponent } from './components/specmate.component';
import { NavigationBarModule } from '../navigation/modules/navigation-bar/navigation-bar.module';
import { ProjectExplorerModule } from '../navigation/modules/project-explorer/project-explorer.module';
import { PropertiesEditorModule } from '../views/side/modules/properties-editor/properties-editor.module';
import { LinksActionsModule } from '../views/side/modules/links-actions/links-actions.module';
import { LogListModule } from '../views/side/modules/log-list/log-list.module';
import { ViewControllerModule } from '../views/controller/modules/view-controller/view-controller.module';
import { SelectedElementModule } from '../views/side/modules/selected-element/selected-element.module';
import { NavigatorModule } from '../navigation/modules/navigator/navigator.module';
import { DataServiceModule } from '../data/modules/data-service/data-service.module';
import { SpecmateRoutingModule } from './routing/specmate-routing.module';
import { ModalsModule } from '../notification/modules/modals/modals.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateRoutingModule,
    NavigationBarModule,
    AngularSplitModule,
    ProjectExplorerModule,
    PropertiesEditorModule,
    LinksActionsModule,
    LogListModule,
    ViewControllerModule,
    SelectedElementModule,
    NavigatorModule,
    DataServiceModule,
    ModalsModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    SpecmateComponent
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
    SpecmateComponent
  ]
})

export class SpecmateModule { }