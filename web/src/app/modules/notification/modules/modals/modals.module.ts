import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModal } from './services/confirmation-modal.service';
import { ErrorNotificationModalService } from './services/error-notification-modal.service';
import { BrowserModule } from '@angular/platform-browser';
import { TypedModalContent } from './components/typed-modal-content.component';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';

@NgModule({
    imports: [
        // MODULE IMPORTS
        NgbModule.forRoot(),
        SpecmateSharedModule,
        BrowserModule
    ],
    declarations: [
        // COMPONENTS IN THIS MODULE
        TypedModalContent
    ],
    exports: [
      // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ],
    providers: [
        // SERVICES
        ConfirmationModal,
        ErrorNotificationModalService
    ],
    bootstrap: [
        // COMPONENTS THAT ARE BOOTSTRAPPED HERE
    ],
    entryComponents: [
        // ENTRY POINTS
        TypedModalContent
    ]
})
export class ModalsModule { }
