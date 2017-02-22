import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SpecmateComponent } from './specmate.component';
import { SpecmateNavigationBar } from './components/navigation-bar.component';
import { RequirementsPerspective } from './components/perspectives/requirements-perspective.component'

import { SpecmateRoutingModule } from './specmate-routing.component'


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    SpecmateRoutingModule
  ],
  declarations: [
    SpecmateComponent,
    RequirementsPerspective,
    SpecmateNavigationBar
  ],
  providers: [],
  bootstrap: [SpecmateComponent]
})

export class SpecmateModule { }