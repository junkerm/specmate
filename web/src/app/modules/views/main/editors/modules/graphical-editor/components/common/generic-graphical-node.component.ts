import { Component, Input, OnInit } from '@angular/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[generic-graphical-node]',
    templateUrl: 'generic-graphical-node.component.html',
    styleUrls: ['generic-graphical-node.component.css']
})

export class GenericGraphicalNode  implements OnInit {
    @Input()
    public zoom: number;

    @Input()
    public node: { className: string, x: number, y: number};

    constructor() {}

    ngOnInit() {}
}
