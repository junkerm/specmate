import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from "@angular/forms";
import { GenericForm } from "./generic-form.component";
import { FormTextInput } from "./form-text-input.component";
import { FormLongTextInput } from "./form-long-text-input.component";
import { FormCheckboxInput } from "./form-checkbox-input.component";
import { FormSingleSelectionInput } from "./form-single-selection-input.component";
import { ConfirmationModalContent } from "./confirmation-modal-content.component";
import { ConfirmationModal } from "./confirmation-modal.service";

@NgModule({
    imports: [
        ReactiveFormsModule,
        BrowserModule
    ],
    declarations: [
        GenericForm,
        FormTextInput,
        FormLongTextInput,
        FormCheckboxInput,
        FormSingleSelectionInput,
        ConfirmationModalContent
    ],
    providers: [ConfirmationModal],
    bootstrap: [],
    exports: [
        GenericForm,
        FormTextInput,
        FormLongTextInput,
        FormCheckboxInput,
        FormSingleSelectionInput,
        ConfirmationModalContent  
    ],
    entryComponents: [ConfirmationModalContent]
})

export class FormsModule { }