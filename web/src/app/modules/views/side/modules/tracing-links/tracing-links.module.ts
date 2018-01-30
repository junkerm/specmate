import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { NgModule } from '@angular/core';
import { GenericFormModule } from '../../../../forms/modules/generic-form/generic-form.module';
import { BrowserModule } from '@angular/platform-browser';
import { TracingLinks } from './components/tracing-links.component';
import { TracingLink } from './components/tracing-link.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IconsModule } from '../../../../common/modules/icons/icons.module';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    IconsModule,
    NgbModule.forRoot(),
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TracingLinks,
    TracingLink
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TracingLinks
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TracingLinksModule { }
