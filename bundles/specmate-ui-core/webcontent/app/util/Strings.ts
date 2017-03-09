import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
    transform(str: string, length: number) {
        var ellipsis: string = "...";
        if(str.length > length + ellipsis.length) {
            return str.slice(0, length) + ellipsis;
        }
        return str;
    }
}
