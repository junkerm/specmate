import { Component, Input } from '@angular/core';
import { Angles } from '../../util/angles';

@Component({
    moduleId: module.id.toString(),
    selector: '[arrow-tip]',
    templateUrl: 'arrow-tip.component.html',
    styleUrls: ['arrow-tip.component.css']
})
export class ArrowTip {
    @Input()
    public position: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public angle: number;

    @Input()
    public fill: boolean;
}
