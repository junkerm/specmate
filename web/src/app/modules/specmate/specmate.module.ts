import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AngularSplitModule } from 'angular-split';
import { CookieModule } from 'ngx-cookie';
import { ValidationModule } from '../../validation/validation.module';
import { DataServiceModule } from '../data/modules/data-service/data-service.module';
import { NavigationBarModule } from '../navigation/modules/navigation-bar/navigation-bar.module';
import { NavigatorModule } from '../navigation/modules/navigator/navigator.module';
import { ProjectExplorerModule } from '../navigation/modules/project-explorer/project-explorer.module';
import { ModalsModule } from '../notification/modules/modals/modals.module';
import { ViewControllerModule } from '../views/controller/modules/view-controller/view-controller.module';
import { AuthModule } from '../views/main/authentication/modules/auth/auth.module';
import { LoginModule } from '../views/main/authentication/modules/login/login.module';
import { HistoryViewModule } from '../views/side/modules/history-view/history-view.module';
import { LinksActionsModule } from '../views/side/modules/links-actions/links-actions.module';
import { LocalHistoryModule } from '../views/side/modules/local-history/local-history.module';
import { LogListModule } from '../views/side/modules/log-list/log-list.module';
import { PropertiesEditorModule } from '../views/side/modules/properties-editor/properties-editor.module';
import { SelectedElementModule } from '../views/side/modules/selected-element/selected-element.module';
import { TracingLinksModule } from '../views/side/modules/tracing-links/tracing-links.module';
import { SpecmateComponent } from './components/specmate.component';
import { SpecmateRoutingModule } from './routing/specmate-routing.module';
import { ErrorsWarningsModule } from '../views/side/modules/errors-warnings/errors-warnings.module';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
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
    ErrorsWarningsModule,
    LogListModule,
    LocalHistoryModule,
    ViewControllerModule,
    SelectedElementModule,
    NavigatorModule,
    DataServiceModule,
    ModalsModule,
    AuthModule,
    LoginModule,
    CookieModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
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
