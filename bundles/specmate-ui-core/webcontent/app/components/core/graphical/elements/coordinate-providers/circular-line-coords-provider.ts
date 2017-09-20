import { LineCoordsProviderBase } from "./line-coords-provider-base";

export class CircularLineCoordsProvider extends LineCoordsProviderBase {

    constructor(source: {x: number, y: number}, target: {x: number, y: number}, private radius: number) {
        super(source, target);
    }

    protected getLineStart(): { x: number; y: number; } {
        return this.coords(this.source, true);
    }
    protected getLineEnd(): { x: number; y: number; } {
        return this.coords(this.target, false);
    }

    private coords(node: {x: number, y: number}, start: boolean): {x: number, y: number} {
        let sig: number = -1;
        if(start) {
            sig = 1;
        }
        return {
            x: node.x + Math.cos(this.angle / 360 * 2 * Math.PI) * this.radius * sig,
            y: node.y + Math.sin(this.angle / 360 * 2 * Math.PI) * this.radius * sig
        };
    }
}