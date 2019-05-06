import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { IconsModule } from '../../../../common/modules/icons/icons.module';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { TracingLink } from './components/tracing-link.component';
import { TracingLinks } from './components/tracing-links.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    IconsModule,
    SpecmateSharedModule,
    NgbModule.forRoot()
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
