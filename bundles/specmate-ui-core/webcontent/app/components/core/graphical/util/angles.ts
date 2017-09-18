export class Angles {
    public static angle(l: {lineStartX: number, lineStartY: number, lineEndX: number, lineEndY: number}): number {
        return Angles.calcAngle(l.lineEndX - l.lineStartX, l.lineEndY - l.lineStartY);
    }

    public static calcAngle(dx: number, dy: number): number {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    }
}