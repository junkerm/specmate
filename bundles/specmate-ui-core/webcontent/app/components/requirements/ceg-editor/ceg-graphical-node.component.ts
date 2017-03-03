import { Component, Input } from '@angular/core';
import { CEGNode } from '../../../model/CEGNode';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg'
})

export class CEGGraphicalNode {

    @Input()
    node: CEGNode;

    @Input()
    x: number;

    @Input()
    y: number;
    
    constructor() { }
}