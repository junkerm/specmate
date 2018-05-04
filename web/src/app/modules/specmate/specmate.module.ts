import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AngularSplitModule } from 'angular-split';
import { SpecmateComponent } from './components/specmate.component';
import { NavigationBarModule } from '../navigation/modules/navigation-bar/navigation-bar.module';
import { ProjectExplorerModule } from '../navigation/modules/project-explorer/project-explorer.module';
import { PropertiesEditorModule } from '../views/side/modules/properties-editor/properties-editor.module';
import { HistoryViewModule } from '../views/side/modules/history-view/history-view.module';
import { TracingLinksModule } from '../views/side/modules/tracing-links/tracing-links.module';
import { LinksActionsModule } from '../views/side/modules/links-actions/links-actions.module';
import { LogListModule } from '../views/side/modules/log-list/log-list.module';
import { ViewControllerModule } from '../views/controller/modules/view-controller/view-controller.module';
import { SelectedElementModule } from '../views/side/modules/selected-element/selected-element.module';
import { NavigatorModule } from '../navigation/modules/navigator/navigator.module';
import { DataServiceModule } from '../data/modules/data-service/data-service.module';
import { SpecmateRoutingModule } from './routing/specmate-routing.module';
import { ModalsModule } from '../notification/modules/modals/modals.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ValidationModule } from '../../validation/validation.module';
import { LocalHistoryModule } from '../views/side/modules/local-history/local-history.module';
import { AuthModule } from '../views/main/authentication/modules/auth/auth.module';
import { LoginModule } from '../views/main/authentication/modules/login/login.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateRoutingModule,
    NavigationBarModule,
    AngularSplitModule,
    ProjectExplorerModule,
    PropertiesEditorModule,
    HistoryViewModule,
    TracingLinksModule,
    LinksActionsModule,
    LogListModule,
    LocalHistoryModule,
    ViewControllerModule,
    SelectedElementModule,
    NavigatorModule,
    DataServiceModule,
    ModalsModule,
    AuthModule,
    LoginModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [HttpClient]
      }
    }),
    ValidationModule
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

// required for AOT compilation
export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, '/i18n/');
}
