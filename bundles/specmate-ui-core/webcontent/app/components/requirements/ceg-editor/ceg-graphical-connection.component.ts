import { Component, Input } from '@angular/core';

import { CEGGraphicalNode } from './ceg-graphical-node.component';

@Component({
    moduleId: module.id,
    selector: 'ceg-graphical-connection',
    templateUrl: 'ceg-graphical-connection.component.svg'
})
export class CEGGraphicalConnection {

    @Input()
    source: CEGGraphicalNode;

    @Input()
    target: CEGGraphicalNode;

    constructor() { }

}