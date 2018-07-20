export class Area {

    constructor() { }

    public static checkPointInArea(area: Square, point: Point) {
        return (point.x >= area.left && point.x <= area.right &&
                point.y >= area.top && point.y <= area.bottom);
    }

    public static checkSquareInArea(area: Square, square: Square) {
        return !( square.left > area.right ||
                  square.right < area.left ||
                  square.top > area.bottom ||
                  square.bottom < area.top);
    }

    public static checkLineInArea(area: Square, line: Line) {
        // Liang–Barsky Algorithm
        let p1 = -(line.x2 - line.x1);
        let p2 = -p1;
        let p3 = -(line.y2 - line.y1);
        let p4 = -p3;

        let q1 = line.x1 - area.left;
        let q2 = area.right - line.x1;
        let q3 = line.y1 - area.top;
        let q4 = area.bottom - line.y1;

        let posArr = [1, 0, 0];
        let negArr = [0, 0, 0];
        let posInd = 1;
        let negInd = 1;

        if ((p1 === 0 && q1 < 0) || (p3 === 0 && q3 < 0)) {
            // Line is parallel to area
            return false;
        }

        if (p1 !== 0) {
            let r1 = q1 / p1;
            let r2 = q2 / p2;
            if (p1 < 0) {
                negArr[negInd++] = r1;
                posArr[posInd++] = r2;
            } else {
                negArr[negInd++] = r2;
                posArr[posInd++] = r1;
            }
        }

        if (p3 !== 0) {
            let r3 = q3 / p3;
            let r4 = q4 / p4;
            if (p3 < 0) {
                negArr[negInd++] = r3;
                posArr[posInd++] = r4;
            } else {
                negArr[negInd++] = r4;
                posArr[posInd++] = r3;
            }
        }

        let rn1 = Math.max(...negArr.slice(0, negInd));
        let rn2 = Math.min(...posArr.slice(0, posInd));

        if (rn1 > rn2) {
            // Line outside the area
            return false;
        }
        // Line intersects area
        return true;
    }
}

export class Square {
    constructor(public left: number, public top: number, public right: number, public bottom: number) { }
}

export class Line {
    constructor(public x1: number, public y1: number, public x2: number, public y2: number) { }
}

export class Point {
    constructor(public x: number, public y: number) { }
}
