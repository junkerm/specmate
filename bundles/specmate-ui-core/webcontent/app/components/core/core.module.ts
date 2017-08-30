import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { NavigationBar } from './common/navigation-bar.component';
import { OperationMonitor } from './common/operation-monitor.component';
import { CommonControls } from './common/common-controls.component'
import { ElementTree } from './explorer/element-tree.component';
import { ProjectExplorer } from './explorer/project-explorer.component';
import { LogList } from './logging/log-list.component'

import { ConfirmationModalContent } from './notification/confirmation-modal-content.component';

import { PipeModule } from '../../pipes/pipe.module';
import { NavigationTargetDirective } from "../../directives/navigation-target.directive";

@NgModule({
    imports: [
        BrowserModule,
        RouterModule,
        PipeModule,
        NgbModule.forRoot()
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        OperationMonitor,
        CommonControls,
        LogList,
        NavigationTargetDirective,
        ConfirmationModalContent
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
        PipeModule,
        NavigationTargetDirective
    ],
  entryComponents: [ConfirmationModalContent]
})

export class CoreModule { }