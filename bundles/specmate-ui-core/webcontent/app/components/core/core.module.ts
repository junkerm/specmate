import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { NavigationBar } from './common/navigation-bar.component';
import { OperationMonitor } from './common/operation-monitor.component';
import { CommonControls } from './common/common-controls.component'
import { ElementTree } from './explorer/element-tree.component';
import { ProjectExplorer } from './explorer/project-explorer.component';
import { LogList } from './logging/log-list.component';
import { LogEntry } from "./logging/log-entry.component";

import { ConfirmationModalContent } from './notification/confirmation-modal-content.component';
import { ErrorModalContent } from "./notification/error-modal-content.component";

import { PipeModule } from '../../pipes/pipe.module';
import { NavigationTargetDirective } from "../../directives/navigation-target.directive";

import { TestSpecificationGeneratorButton } from './common/test-specification-generator-button.component';


@NgModule({
    imports: [
        BrowserModule,
        RouterModule,
        PipeModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        OperationMonitor,
        CommonControls,
        LogList,
        LogEntry,
        NavigationTargetDirective,
        ConfirmationModalContent,
        ErrorModalContent,
        TestSpecificationGeneratorButton
    ],
    providers: [],
    bootstrap: [],
    exports: [
        BrowserModule,
        RouterModule,
        ProjectExplorer,
        NavigationBar,
        OperationMonitor,
        CommonControls,
        LogList,
        LogEntry,
        PipeModule,
        NavigationTargetDirective,
        TestSpecificationGeneratorButton
    ],
  entryComponents: [ConfirmationModalContent, ErrorModalContent]
})

export class CoreModule { }