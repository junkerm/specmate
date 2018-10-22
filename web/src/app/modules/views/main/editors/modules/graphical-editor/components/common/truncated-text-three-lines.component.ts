import { Component, Input } from '@angular/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[truncated-text-three-lines]',
    templateUrl: 'truncated-text-three-lines.component.svg',
    styleUrls: ['truncated-text-three-lines.component.css']
})
export class TruncatedTextThreeLines {

    @Input()
    public position: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public text: string;

    @Input()
    public ellipsis = '...';

    @Input()
    public width: number;

    @Input()
    public centered = true;

    private _adjustedText: string[] = ['', '', ''];
    public get adjustedText(): string[] {
        if (this.stringWidth(this.text) <= this.width) {
            this._adjustedText[0] = this.text;
        }
        let endOfFirstLine = this.width / 10;
        if (this.stringWidth(this.text) <= 2 * this.width) {
            this._adjustedText[0] = this.text.slice(0, endOfFirstLine);
            this._adjustedText[1] = this.text.slice(endOfFirstLine);
        }
        let endOfSecondLine = 2 * this.width / 10;
        if (this.stringWidth(this.text) <= 3 * this.width) {
            this._adjustedText[0] = this.text.slice(0, endOfFirstLine);
            this._adjustedText[1] = this.text.slice(endOfFirstLine, endOfSecondLine);
            this._adjustedText[2] = this.text.slice(endOfSecondLine);
        } else {
            let ellipsisWidth: number = this.stringWidth(this.ellipsis);
            let endfThirdLine = (3 * this.width - ellipsisWidth) / 10;
            this._adjustedText[0] = this.text.slice(0, endOfFirstLine);
            this._adjustedText[1] = this.text.slice(endOfFirstLine, endOfSecondLine);
            this._adjustedText[2] = this.text.slice(endOfSecondLine, endfThirdLine) + this.ellipsis;
        }
        return this._adjustedText;
    }

    private stringWidth(str: string): number {
        return str.length * 10;
    }
}
