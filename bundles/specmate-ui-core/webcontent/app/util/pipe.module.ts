import { NgModule } from '@angular/core';

import { OfTypePipe } from './Type';
import { TruncatePipe } from './Strings';


@NgModule({
    imports: [],
    exports: [OfTypePipe, TruncatePipe],
    declarations: [OfTypePipe, TruncatePipe],
    providers: [],
})
export class PipeModule { }
