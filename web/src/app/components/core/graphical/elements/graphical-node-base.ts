import { GraphicalElementBase } from './graphical-element-base';
import { ISpecmatePositionableModelObject } from '../../../../model/ISpecmatePositionableModelObject';

export abstract class GraphicalNodeBase<T extends ISpecmatePositionableModelObject> extends GraphicalElementBase<T> {

    public abstract get dimensions(): {width: number, height: number};

    public get center(): { x: number; y: number; } {
        return {
            x: this.element.x,
            y: this.element.y
        };
    }
    public get topLeft(): { x: number; y: number; } {
        return {
            x: this.center.x - this.dimensions.width / 2,
            y: this.center.y - this.dimensions.height / 2
        };
    }
}