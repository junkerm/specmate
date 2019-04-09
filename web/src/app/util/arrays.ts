import { Sort } from './sort';

export class Arrays {
    public static remove(array: any[], element: any): void {
        if (!array) {
            return;
        }
        let index = array.indexOf(element);
        if (index >= 0) {
            array.splice(index, 1);
        }
    }

    public static contains<T>(array: T[], element: T): boolean {
        if (!array) {
            return false;
        }
        return array.indexOf(element) >= 0;
    }

    public static groupBy<T>(array: T[], keyFunction: ((elem: T) => string) | ((elem: T) => number) ): T[][] {
        let out: T[][] = [];
        for (const elem of array) {
            let key = keyFunction(elem);
            if (out[key] === undefined) {
                out[key] = [];
            }
            out[key].push(elem);
        }
        return out;
    }

    public static flatten<T>(array: T[][]): T[] {
        return [].concat(...array);
    }
}
