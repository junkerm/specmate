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

    public static contains(array: any[], element: any): boolean {
        return array.indexOf(element) >= 0;
    }
}
