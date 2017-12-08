import { LineCoordsProviderBase } from './line-coords-provider-base';

export class DiamondLineCoordsProvider extends LineCoordsProviderBase {

    constructor(source: { x: number, y: number }, target: { x: number, y: number }, private dimension: number) {
        super(source, target);
    }

    protected getLineStart(): {x: number, y: number} {
        let x: number = this.x(this.target, this.source);
        let y: number = this.y(this.target, this.source);
        return {
            x: this.source.x + x,
            y: this.source.y + y
        };
    }

    protected getLineEnd(): {x: number, y: number} {
        let x: number = this.x(this.source, this.target);
        let y: number = this.y(this.source, this.target);
        return {
            x: this.target.x + x,
            y: this.target.y + y
        };
    }

    private a1(source: {x: number, y: number}, target: {x: number, y: number}): number {
        if (this.q1(source, target) || this.q3(source, target)) {
            return 1;
        }
        return -1;
    }

    private c1(source: {x: number, y: number}, target: {x: number, y: number}): number {
        if (this.q1(source, target) || this.q2(source, target)) {
            return this.dimension;
        }
        return -this.dimension;
    }

    private a2(source: {x: number, y: number}, target: {x: number, y: number}): number {
        return (source.y - target.y) / (source.x - target.x);
    }

    private c2(source: {x: number, y: number}, target: {x: number, y: number}): number {
        return 0;
    }

    private x(source: {x: number, y: number}, target: {x: number, y: number}): number {
        return (this.c2(source, target) - this.c1(source, target)) / (this.a1(source, target) - this.a2(source, target));
    }

    private y(source: {x: number, y: number}, target: {x: number, y: number}): number {
        return this.a1(source, target) * this.x(source, target) + this.c1(source, target);
    }

    private q1(source: {x: number, y: number}, target: {x: number, y: number}): boolean {
        return source.x <= target.x && source.y > target.y;
    }

    private q2(source: {x: number, y: number}, target: {x: number, y: number}): boolean {
        return source.x > target.x && source.y >= target.y;
    }

    private q3(source: {x: number, y: number}, target: {x: number, y: number}): boolean {
        return source.x >= target.x && source.y < target.y;
    }

    private q4(source: {x: number, y: number}, target: {x: number, y: number}): boolean {
        return source.x < target.x && source.y <= target.y;
    }
}
