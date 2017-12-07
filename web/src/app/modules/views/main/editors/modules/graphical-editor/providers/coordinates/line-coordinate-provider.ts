import { LineCoordsProviderBase } from './line-coords-provider-base';
import { Type } from '../../../../../../../../util/type';
import { Config } from '../../../../../../../../config/config';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { ProcessDecision } from '../../../../../../../../model/ProcessDecision';
import { ProcessStart } from '../../../../../../../../model/ProcessStart';
import { ProcessEnd } from '../../../../../../../../model/ProcessEnd';
import { RectangularLineCoordsProvider } from './rectangular-line-coords-provider';
import { DiamondLineCoordsProvider } from './diamond-line-coords-provider';
import { CircularLineCoordsProvider } from './circular-line-coords-provider';

export class LineCoordinateProvider {

    public static provide(
        type: {className: string},
        source: {x: number, y: number},
        target: {x: number, y: number}): LineCoordsProviderBase {

        if (Type.is(type, CEGNode)) {
            return new RectangularLineCoordsProvider(source, target, {width: Config.CEG_NODE_WIDTH, height: Config.CEG_NODE_HEIGHT});
        }
        if (Type.is(type, ProcessStep)) {
            return new RectangularLineCoordsProvider(source, target, {width: Config.CEG_NODE_WIDTH, height: Config.CEG_NODE_HEIGHT});
        }
        if (Type.is(type, ProcessDecision)) {
            return new DiamondLineCoordsProvider(source, target, Config.PROCESS_DECISION_NODE_DIM);
        }
        if (Type.is(type, ProcessStart)) {
            return new CircularLineCoordsProvider(source, target, Config.PROCESS_START_END_NODE_RADIUS);
        }
        if (Type.is(type, ProcessEnd)) {
            return new CircularLineCoordsProvider(source, target, Config.PROCESS_START_END_NODE_RADIUS);
        }
    }
}
