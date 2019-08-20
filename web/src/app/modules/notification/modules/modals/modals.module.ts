import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { TypedModalContent } from './components/typed-modal-content.component';
import { ConfirmationModal } from './services/confirmation-modal.service';
import { ErrorNotificationModalService } from './services/error-notification-modal.service';
import { LoadingModalService } from './services/loading-model-service';
import { LoadingModalContent } from './components/loading-modal-content.component';

@NgModule({
    imports: [
        // MODULE IMPORTS
        NgbModule.forRoot(),
        SpecmateSharedModule,
        BrowserModule
    ],
    declarations: [
        // COMPONENTS IN THIS MODULE
        TypedModalContent,
        LoadingModalContent
    ],
    exports: [
      // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ],
    providers: [
        // SERVICES
        ConfirmationModal,
        ErrorNotificationModalService,
        LoadingModalService
    ],
    bootstrap: [
        // COMPONENTS THAT ARE BOOTSTRAPPED HERE
    ],
    entryComponents: [
        // ENTRY POINTS
        TypedModalContent,
        LoadingModalContent
    ]
})
export class ModalsModule { }
