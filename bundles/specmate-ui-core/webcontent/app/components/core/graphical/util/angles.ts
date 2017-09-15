export class Angles {
    public static angle(l: {x1: number, y1: number, x2: number, y2: number}): number {
        return Angles.calcAngle(l.x2 - l.x1, l.y2 - l.y1);
    }

    public static calcAngle(dx: number, dy: number): number {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    }
}