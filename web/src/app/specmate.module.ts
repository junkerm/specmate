import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularSplitModule } from 'angular-split';

import { SpecmateDataService } from './services/data/specmate-data.service';
import { EditorCommonControlService } from './services/common-controls/editor-common-control.service'
import { NavigatorService } from "./services/navigation/navigator.service";

import { SpecmateComponent } from './specmate.component';
import { PageNotFound } from './components/page-not-found.component';
import { Welcome } from "./components/welcome.component";

import { CoreModule } from './components/core/core.module';
import { RequirementsModule } from './components/requirements/requirements.module';
import { SpecmateRoutingModule } from './specmate-routing.module';
import { TestsModule} from './components/tests/tests.module';

import { UnsavedChangesGuard } from './guards/unsaved-changes-guard';

@NgModule({
  imports: [
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    CoreModule,
    RequirementsModule,
    TestsModule,
    SpecmateRoutingModule,
    AngularSplitModule
  ],
  declarations: [
    SpecmateComponent,
    PageNotFound,
    Welcome
  ],
  providers: [SpecmateDataService, EditorCommonControlService, NavigatorService, UnsavedChangesGuard],
  bootstrap: [SpecmateComponent]
})

export class SpecmateModule { }