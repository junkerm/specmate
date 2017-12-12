import { Angles } from '../../util/angles';

export abstract class LineCoordsProviderBase {
    constructor(protected source: {x: number, y: number}, protected target: {x: number, y: number}) { }

    protected abstract getLineStart(): {x: number, y: number};
    protected abstract getLineEnd(): {x: number, y: number};

    public get center(): {x: number, y: number} {
        return {
            x: (this.lineStart.x + this.lineEnd.x) / 2.0,
            y: (this.lineStart.y + this.lineEnd.y) / 2.0
        };
    }

    public get lineStart(): {x: number, y: number} {
        return this.getLineStart();
    }

    public get lineEnd(): {x: number, y: number} {
        return this.getLineEnd();
    }

    public get angle(): number {
        return Angles.angle(this.source.x, this.source.y, this.target.x, this.target.y);
    }
}
