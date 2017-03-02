export class Type {
    public static is(o1: any, o2: any): boolean {
        return o1.className === o2.className;
    }
}