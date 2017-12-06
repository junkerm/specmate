import { Sort } from "./sort";

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
        if(!array) {
            return false;
        }
        return array.indexOf(element) >= 0; 
    }
}
