import { Pipe, PipeTransform } from '@angular/core';
import { Strings } from '../../../../../util/strings';

@Pipe({ name: 'truncate', pure: false })
export class TruncatePipe implements PipeTransform {
    transform(str: string, length: number) {
        return Strings.truncate(str, length);
    }
}
