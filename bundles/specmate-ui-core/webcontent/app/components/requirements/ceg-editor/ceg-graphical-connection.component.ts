import { Component, Input } from '@angular/core';

import { CEGGraphicalNode } from './ceg-graphical-node.component';
import { Proxy } from '../../../model/support/proxy';
import { SpecmateDataService } from '../../../services/specmate-data.service';

@Component({
    moduleId: module.id,
    selector: 'ceg-graphical-connection',
    templateUrl: 'ceg-graphical-connection.component.svg'
})
export class CEGGraphicalConnection {

    @Input()
    sourceProxy: Proxy;

    @Input()
    targetProxy: Proxy;


    constructor(private dataService: SpecmateDataService) { }

    get originX(): number {
        this.dataService.getDetails(this.sourceProxy.url);
        return 0;
    }

}