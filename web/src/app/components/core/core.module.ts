import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { NavigationBar } from './navigation-bar.component';
import { OperationMonitor } from './operation-monitor.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';
import { UrlBreadcrumb } from './url-breadcrumb.component';
import { ConfirmationModal } from './confirmation-modal.service';
import { ConfirmationModalContent } from './confirmation-modal-content.component';

import { PipeModule } from '../../util/pipe.module';

@NgModule({
    imports: [
        BrowserModule,
        RouterModule,
        PipeModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        UrlBreadcrumb,
        ConfirmationModalContent,
        OperationMonitor
    ],
    providers: [ConfirmationModal],
    bootstrap: [],
    exports: [
        BrowserModule,
        RouterModule,
        ProjectExplorer,
        NavigationBar,
        OperationMonitor,
        UrlBreadcrumb,
        PipeModule
    ],
    entryComponents: [ConfirmationModalContent]
})

export class CoreModule { }