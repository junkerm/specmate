import { Component } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    selector: 'navigation-bar',
    moduleId: module.id.toString(),
    templateUrl: 'navigation-bar.component.html'
})
export class NavigationBar {
    constructor(private dataService: SpecmateDataService) { }
}
