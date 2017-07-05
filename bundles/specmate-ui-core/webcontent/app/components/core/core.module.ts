import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule} from '@angular/forms';

import { NavigationBar } from './navigation-bar.component';
import { OperationMonitor } from './operation-monitor.component';
import { ElementTree } from './element-tree.component';
import { ProjectExplorer } from './project-explorer.component';
import { UrlBreadcrumb } from './url-breadcrumb.component';
import { CommonControls } from './common-controls.component'

import { GenericForm } from "./forms/generic-form.component";
import { FormTextInput } from './forms/form-text-input.component';
import { FormLongTextInput } from './forms/form-long-text-input.component';
import { FormCheckboxInput } from './forms/form-checkbox-input.component';
import { FormSingleSelectionInput } from "./forms/form-single-selection-input.component";

import { PipeModule } from '../../util/pipe.module';
import { FormsModule } from "./forms/forms.module";

@NgModule({
    imports: [
        BrowserModule,
        RouterModule,
        PipeModule,
        FormsModule
    ],
    declarations: [
        NavigationBar,
        ProjectExplorer,
        ElementTree,
        UrlBreadcrumb,
        OperationMonitor,
        CommonControls
    ],
    providers: [],
    bootstrap: [],
    exports: [
        BrowserModule,
        RouterModule,
        ProjectExplorer,
        NavigationBar,
        OperationMonitor,
        UrlBreadcrumb,
        FormsModule,
        CommonControls,
        PipeModule
    ],
    entryComponents: []
})

export class CoreModule { }