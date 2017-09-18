export class RectangularLineProvider {
    constructor(private nodeWidth: number, private nodeHeight: number) { }

    public get center(): {x: number, y: number} {
        return {x: 0, y: 0};
    }

    public get lineStart(): {x: number, y: number} {
        return {x: 0, y: 0};
    }

    public get lineEnd(): {x: number, y: number} {
        return {x: 0, y: 0};
    }
}