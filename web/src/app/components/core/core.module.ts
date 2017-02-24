import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { NavigationBar } from './navigation-bar.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';
import { UrlBreadcrumb } from './url-breadcrumb.component',

@NgModule({
    imports: [
        BrowserModule,
        RouterModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        UrlBreadcrumb
    ],
    providers: [],
    bootstrap: [],
    exports: [
        BrowserModule,
        ProjectExplorer,
        NavigationBar,
        UrlBreadcrumb
    ]
})

export class CoreModule { }