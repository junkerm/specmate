import { Pipe, PipeTransform } from '@angular/core';

export class Type {
    public static is(o1: any, o2: any): boolean {
        if (o1 && o2 && o1.className && o2.className) {
            return o1.className === o2.className;
        }
        return false;
    }

    public static getTypeName(o: any): string {
        if (o) {
            return o.className;
        }
        return null;
    }
}

@Pipe({ name: 'ofTypeName', pure: false })
export class OfTypeNamePipe implements PipeTransform {
    transform(objs: any[], typeName: string) {
        if(objs) {
            return objs.filter(o => Type.getTypeName(o) === typeName);
        }
        return [];
    }
}

@Pipe({ name: 'ofType', pure: false })
export class OfTypePipe implements PipeTransform {
    transform(objs: any[], type: any) {
        if(objs) {
            return objs.filter(o => Type.is(o, type));
        }
        return [];
    }
}