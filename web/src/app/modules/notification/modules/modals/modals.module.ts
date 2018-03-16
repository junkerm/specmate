import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModal } from './services/confirmation-modal.service';
import { ErrorNotificationModalService } from './services/error-notification-modal.service';
import { ConfirmationModalContent } from './components/confirmation-modal-content.component';
import { ErrorModalContent } from './components/error-modal-content.component';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        // MODULE IMPORTS
        NgbModule.forRoot(),
        BrowserModule,
        TranslateModule
    ],
    declarations: [
        // COMPONENTS IN THIS MODULE
        ConfirmationModalContent,
        ErrorModalContent
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
        ConfirmationModalContent, ErrorModalContent
    ]
})
export class ModalsModule { }
