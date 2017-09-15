export class Coords {
    public static getCenter(x: number, y: number, width: number, height: number): {x: number, y: number} {
        let cx: number = Number.parseFloat((x + width / 2) + '');
        let cy: number = Number.parseFloat((y + height / 2) + '');
        return {
            x: cx,
            y: cy
        };
    }
}