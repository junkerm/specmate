import { NgModule } from '@angular/core';

import { TestsPerspective } from './tests-perspective.component';
//import { TestSpecificationEditor } from './test-specification-editor.component';

import { CoreModule } from '../core/core.module';
import { TestsRoutingModule } from './tests-routing.module';

@NgModule({
    imports: [
        CoreModule,
        TestsRoutingModule,
       // TestSpecificationEditor 
    ],
    declarations: [
        TestsPerspective,
       // TestSpecificationEditor
    ],
    providers: [],
    exports: [],
})
export class TestsModule { }
