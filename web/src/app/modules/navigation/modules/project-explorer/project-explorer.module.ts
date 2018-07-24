import { NgModule } from '@angular/core';
import { ProjectExplorer } from './components/project-explorer.component';
import { BrowserModule } from '@angular/platform-browser';
import { ElementTree } from './components/element-tree.component';
import { NavigatorModule } from '../navigator/navigator.module';
import { IconsModule } from '../../../common/modules/icons/icons.module';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    IconsModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ProjectExplorer,
    ElementTree
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ProjectExplorer
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ProjectExplorerModule { }
