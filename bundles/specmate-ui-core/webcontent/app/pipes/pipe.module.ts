import { NgModule } from '@angular/core';

import { OfTypePipe } from '../util/Type';
import { TruncatePipe } from './truncate-pipe';


@NgModule({
    imports: [],
    exports: [OfTypePipe, TruncatePipe],
    declarations: [OfTypePipe, TruncatePipe],
    providers: [],
})
export class PipeModule { }
