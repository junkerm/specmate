import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

import { NavigationBar } from './navigation-bar.component';
import { OperationMonitor } from './operation-monitor.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';
import { UrlBreadcrumb } from './url-breadcrumb.component';
import { ConfirmationModal } from './confirmation-modal.service';
import { ConfirmationModalContent } from './confirmation-modal-content.component';

import { FormTextInput } from './forms/form-text-input.component';
import { FormLongTextInput } from './forms/form-long-text-input.component';
import { FormCheckboxInput } from './forms/form-checkbox-input.component';


import { PipeModule } from '../../util/pipe.module';

@NgModule({
    imports: [
        BrowserModule,
        RouterModule,
        PipeModule,
        ReactiveFormsModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        UrlBreadcrumb,
        ConfirmationModalContent,
        OperationMonitor,
        FormTextInput,
        FormLongTextInput,
        FormCheckboxInput
    ],
    providers: [ConfirmationModal],
    bootstrap: [],
    exports: [
        BrowserModule,
        RouterModule,
        ReactiveFormsModule,
        ProjectExplorer,
        NavigationBar,
        OperationMonitor,
        UrlBreadcrumb,
        PipeModule,
        FormTextInput,
        FormLongTextInput,
        FormCheckboxInput
    ],
    entryComponents: [ConfirmationModalContent]
})

export class CoreModule { }