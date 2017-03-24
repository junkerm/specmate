import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { NavigationBar } from './navigation-bar.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';
import { UrlBreadcrumb } from './url-breadcrumb.component';
import { ConfirmationModal } from './confirmation-modal.component';

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
        ConfirmationModal
    ],
    providers: [],
    bootstrap: [],
    exports: [
        BrowserModule,
        RouterModule,
        ProjectExplorer,
        NavigationBar,
        UrlBreadcrumb,
        ConfirmationModal,
        PipeModule
    ]
})

export class CoreModule { }