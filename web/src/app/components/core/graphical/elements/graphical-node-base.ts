import { GraphicalElementBase } from './graphical-element-base';
import { ISpecmatePositionableModelObject } from '../../../../model/ISpecmatePositionableModelObject';

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
}