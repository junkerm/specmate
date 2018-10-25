import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { GraphicalElementBase } from './graphical-element-base';
import { Square, Area } from '../util/area';

export abstract class GraphicalNodeBase<T extends ISpecmatePositionableModelObject> extends GraphicalElementBase<T> {

    public abstract get dimensions(): {width: number, height: number};

    public get center(): { x: number; y: number; } {
        return {
            x: this.topLeft.x + this.dimensions.width / 2,
            y: this.topLeft.y + this.dimensions.height / 2
        };
    }

    public get topLeft(): { x: number; y: number; } {
        return {
            x: Math.max(this.element.x - this.dimensions.width / 2, 0),
            y: Math.max(this.element.y - this.dimensions.height / 2, 0)
        };
    }

    public isInSelectionArea(area: Square): boolean {
        let pos = this.topLeft;
        let dim = this.dimensions;
        let rect = new Square(pos.x, pos.y, pos.x + dim.width, pos.y + dim.height);
        return Area.checkSquareInArea(area, rect);
    }
}
