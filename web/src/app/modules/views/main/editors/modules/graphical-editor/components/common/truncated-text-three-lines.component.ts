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
    public height: number;

    @Input()
    public centered = true;

    private _adjustedText: string[] = ['', '', ''];
    // public get adjustedText(): string {
    //     if (this.stringWidth(this.text) <= this.width) {
    //         return this.text;
    //     }
    //     let ellipsisWidth: number = this.stringWidth(this.ellipsis);
    //     for (let i = this.text.length - 1; i >= 0; i--) {
    //         let truncatedText: string = this.text.substring(0, i);
    //         let widthWithEllipsis: number = this.stringWidth(truncatedText) + ellipsisWidth;
    //         if (widthWithEllipsis <= this.width) {
    //             this._adjustedText = truncatedText + this.ellipsis;
    //             break;
    //         }
    //     }
    //     return this._adjustedText;
    // }

    // width = 1000
    public get adjustedText(): string[] {
        if (this.stringWidth(this.text) <= this.width) {
            this._adjustedText[0] = this.text;
        }
        if (this.stringWidth(this.text) <= 2 * this.width) {
            this._adjustedText[0] = this.text.slice(0, this.width / 10);
            this._adjustedText[1] = this.text.slice(this.width / 10);
        }
        if (this.stringWidth(this.text) <= 3 * this.width) {
            this._adjustedText[0] = this.text.slice(0, this.width / 10);
            this._adjustedText[1] = this.text.slice(this.width / 10, 2 * this.width / 10);
            this._adjustedText[2] = this.text.slice(2 * this.width / 10);
        } else {
            let ellipsisWidth: number = this.stringWidth(this.ellipsis);
            this._adjustedText[0] = this.text.slice(0, this.width / 10);
            this._adjustedText[1] = this.text.slice(this.width / 10, 2 * this.width / 10);
            this._adjustedText[2] = this.text.slice(2 * this.width / 10, (3 * this.width - ellipsisWidth) / 10) + this.ellipsis;
        }
        return this._adjustedText;
    }

    private stringWidth(str: string): number {
        return str.length * 10;
    }
}
