import { Pipe, PipeTransform } from '@angular/core';

export class Type {
    public static is(o1: {className: string}, o2: {className: string}): boolean {
        if (o1 && o2 && o1.className && o2.className) {
            return o1.className === o2.className;
        }
        return false;
    }

    public static of(o: {className: string}): string {
        if (o) {
            return o.className;
        }
        return null;
    }
}
