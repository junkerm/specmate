import {TestCaseRow} from './test-case-row.component';
import { NgModule } from '@angular/core';

import { TestsPerspective } from './tests-perspective.component';
//import { TestSpecificationEditor } from './test-specification-editor.component';

import { CoreModule } from '../core/core.module';
import { TestsRoutingModule } from './tests-routing.module';
import { TestSpecificationEditor } from './test-specification-editor.component';

@NgModule({
    imports: [
        CoreModule,
        TestsRoutingModule
    ],
    declarations: [
        TestsPerspective,
        TestSpecificationEditor,
        TestCaseRow
    ],
    providers: [],
    exports: [],
})
export class TestsModule { }
