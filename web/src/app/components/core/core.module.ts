import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { NavigationBar } from './navigation-bar.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';

@NgModule({
    imports: [
        BrowserModule,
        RouterModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree
    ],
    providers: [],
    bootstrap: [],
    exports: [
        BrowserModule,
        ProjectExplorer,
        NavigationBar
    ]
})

export class CoreModule { }