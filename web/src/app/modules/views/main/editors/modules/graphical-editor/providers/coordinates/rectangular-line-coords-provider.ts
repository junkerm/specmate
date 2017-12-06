import { LineCoordsProviderBase } from "./line-coords-provider-base";
import { Angles } from "../../util/angles";

export class RectangularLineCoordsProvider extends LineCoordsProviderBase {
    constructor(source: {x: number, y: number}, target: {x: number, y: number}, private dimensions: {width: number, height: number}) {
        super(source, target);
    }

    protected getLineEnd(): {x: number, y: number} {
        if(this.isLeft) {
            return {
                x: this.target.x - this.dimensions.width / 2,
                y: this.target.y - ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))};
        } else if(this.isRight) {
            return {
                x: this.target.x + this.dimensions.width / 2,
                y: this.target.y + ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        } else if(this.isTop) {
            return {
                x: this.target.x - ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.target.y - this.dimensions.height / 2
            };
        } else if(this.isBelow) {
            return {
                x: this.target.x + ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.target.y + this.dimensions.height / 2
            };
        }
    }

    protected getLineStart(): {x: number, y: number} {
        if(this.isRight) {
            return {
                x: this.source.x - this.dimensions.width / 2,
                y: this.source.y - ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))};
        } else if(this.isLeft) {
            return {
                x: this.source.x + this.dimensions.width / 2,
                y: this.source.y + ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        } else if(this.isBelow) {
            return {
                x: this.source.x - ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.source.y - this.dimensions.height / 2
            };
        } else if(this.isTop) {
            return {
                x: this.source.x + ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.source.y + this.dimensions.height / 2
            };
        }
    }

    private getNodeCenter(node: {x: number, y: number}): {x: number, y: number} {
        return {
            x: node.x + this.dimensions.width / 2,
            y: node.y + this.dimensions.height / 2
        };
    }

    private get alpha1(): number {
        return Angles.calcAngle(-this.dimensions.width, -this.dimensions.height);
    }

    private get isLeft(): boolean {
        return this.angle >= -(180 + this.alpha1) && this.angle <= (180 + this.alpha1)
    }

    private get isRight(): boolean {
        return (this.angle >= -this.alpha1 && this.angle <= 180) || (this.angle >= -180 && this.angle <= this.alpha1)
    }

    private get isTop(): boolean {
        return this.angle >= 180 + this.alpha1 && this.angle <= -this.alpha1;
    }

    private get isBelow(): boolean {
        return this.angle >= this.alpha1 && this.angle <= -(180 + this.alpha1);
    }
}