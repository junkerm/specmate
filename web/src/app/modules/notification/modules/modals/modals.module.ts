import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModal } from './services/confirmation-modal.service';
import { ErrorNotificationModalService } from './services/error-notification-modal.service';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { TypedModalContent } from './components/typed-modal-content.component';

@NgModule({
    imports: [
        // MODULE IMPORTS
        NgbModule.forRoot(),
        BrowserModule,
        TranslateModule
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
