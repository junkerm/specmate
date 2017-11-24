import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularSplitModule } from 'angular-split';
import { DragulaModule } from "ng2-dragula";

import { ConfirmationModalContent } from './components/core/notification/confirmation-modal-content.component';

import { SpecmateDataService } from './services/data/specmate-data.service';
import { EditorCommonControlService } from './services/common-controls/editor-common-control.service'
import { NavigatorService } from './services/navigation/navigator.service';
import { LoggingService } from './services/logging/logging.service';
import { ConfirmationModal } from './services/notification/confirmation-modal.service';
import { ErrorNotificationModalService } from './services/notification/error-notification-modal.service';
import { ViewControllerService } from './services/view/view-controller.service';

import { SpecmateComponent } from './specmate.component';

import { PagesModule } from './components/pages/pages.module';
import { CoreModule } from './components/core/core.module';
import { SpecmateFormsModule } from './components/forms/specmate-forms.module';
import { RequirementsModule } from './components/requirements/requirements.module';
import { SpecmateRoutingModule } from './specmate-routing.module';
import { TestsModule} from './components/tests/tests.module';

import { UnsavedChangesGuard } from './guards/unsaved-changes-guard';
import { ProcessesModule } from "./components/processes/processes.module";

@NgModule({
  imports: [
    FormsModule,
    HttpModule,
    PagesModule,
    CoreModule,
    SpecmateFormsModule,
    RequirementsModule,
    TestsModule,
    ProcessesModule,
    SpecmateRoutingModule,
    AngularSplitModule,
    NgbModule.forRoot(),
    DragulaModule
  ],
  declarations: [
    SpecmateComponent
  ],
  providers: [
    SpecmateDataService,
    EditorCommonControlService,
    NavigatorService,
    LoggingService,
    ConfirmationModal,
    ErrorNotificationModalService,
    ViewControllerService,
    UnsavedChangesGuard
  ],
  bootstrap: [
    SpecmateComponent
  ]
})

export class SpecmateModule { }