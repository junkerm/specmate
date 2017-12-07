import { Component, Input } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: '[truncated-text]',
    templateUrl: 'truncated-text.component.svg',
    styleUrls: ['truncated-text.component.css']
})
export class TruncatedText {

    @Input()
    public position: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public text: string;

    @Input()
    public ellipsis: string = '...';

    @Input()
    public width: number;

    @Input()
    public centered: boolean = true;

    private _adjustedText: string;
    public get adjustedText(): string {
        if (this.stringWidth(this.text) <= this.width) {
            return this.text;
        }
        let ellipsisWidth: number = this.stringWidth(this.ellipsis);
        for (let i = this.text.length - 1; i >= 0; i--) {
            let truncatedText: string = this.text.substring(0, i);
            let widthWithEllipsis: number = this.stringWidth(truncatedText) + ellipsisWidth;
            if (widthWithEllipsis <= this.width) {
                this._adjustedText = truncatedText + this.ellipsis;
                break;
            }
        }
        return this._adjustedText;
    }

    private stringWidth(str: string): number {
        return str.length * 10;
    }
}
