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
        let tmpMap = {};
        for (const elem of array) {
            let key = keyFunction(elem);
            if (tmpMap[key] === undefined) {
                tmpMap[key] = [];
            }
            tmpMap[key].push(elem);
        }

        let out = [];
        for (const key in tmpMap) {
            if (tmpMap.hasOwnProperty(key)) {
                out.push(tmpMap[key]);
            }
        }
        return out;
    }
}
