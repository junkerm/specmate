import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SpecmateDataService } from './services/specmate-data.service';
import { SpecmateComponent } from './specmate.component';
import { PageNotFound } from './components/page-not-found.component';

import { CoreModule } from './components/core/core.module';
import { RequirementsModule } from './components/requirements/requirements.module';
import { SpecmateRoutingModule } from './specmate-routing.module';

import { ConfirmationModal } from './components/core/confirmation-modal.service';


@NgModule({
  imports: [
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    CoreModule,
    RequirementsModule,
    SpecmateRoutingModule,
  ],
  declarations: [
    SpecmateComponent,
    PageNotFound
  ],
  providers: [SpecmateDataService],
  bootstrap: [SpecmateComponent]
})

export class SpecmateModule { }