export class Arrays {
    public static remove(array: any[], element: any): void {
        let index = array.indexOf(element);
        array.splice(index, 1);
    }
}
