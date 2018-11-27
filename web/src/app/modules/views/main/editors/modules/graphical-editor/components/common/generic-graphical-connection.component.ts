import { Component, OnInit, Input } from '@angular/core';
import { IContainer } from '../../../../../../../../model/IContainer';

@Component({
    moduleId: module.id.toString(),
    selector: '[generic-graphical-connection]',
    templateUrl: 'generic-graphical-connection.component.svg',
    styleUrls: ['generic-graphical-connection.component.css']
})

export class GenericGraphicalConnection implements OnInit {
    @Input()
    nodes: IContainer[];

    @Input()
    connection: { className: string };

    constructor() { }

    ngOnInit() { }
}
