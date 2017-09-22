import { Component, Input, ViewChildren, QueryList, ElementRef } from "@angular/core";
import { Angles } from "../../util/angles";
import { Config } from "../../../../../config/config";

@Component({
    moduleId: module.id,
    selector: '[truncated-text]',
    templateUrl: 'truncated-text.component.svg',
    styleUrls: ['truncated-text.component.css']
})
export class TruncatedText {

    @ViewChildren ('dummy')
    public dummy: QueryList<ElementRef>;

    @Input()
    public position: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public text: string;

    @Input()
    public ellipsis: string = '....';

    @Input()
    public fill: boolean;

    @Input()
    public width: number;

    @Input()
    public centered: boolean = true;

    private get dummyElem(): any {
        if(!this.dummy) {
            return undefined;
        }
        return this.dummy.first.nativeElement;
    }

    private _adjustedText: string;
    public get adjustedText(): string {
        if(!this.dummyElem) {
            return '';
        }
        if(this.stringWidth(this.text) <= this.width) {
            return this.text;
        }
        let ellipsisWidth: number = this.stringWidth(this.ellipsis);
        for(let i = this.text.length - 1; i >= 0; i--) {
            let truncatedText: string = this.text.substring(0, i);
            let widthWithEllipsis: number = this.stringWidth(truncatedText) + ellipsisWidth;
            if(widthWithEllipsis <= this.width) {
                this._adjustedText = truncatedText + this.ellipsis;
                break;
            }
        }
        return this._adjustedText;
    }

    private stringWidth(str: string): number {
        if(!this.dummyElem) {
            return 0;
        }
        this.dummyElem.textContent = str;
        return this.dummyWidth;
    }

    private get dummyWidth(): number {
        if(!this.dummyElem) {
            return 0;
        }
        return this.dummyElem.getBBox().width;
    }
}
