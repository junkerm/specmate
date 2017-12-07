import { Component, Input } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { Config } from '../../../../../config/config';

@Component({
    selector: 'navigation-bar',
    moduleId: module.id,
    templateUrl: 'navigation-bar.component.html'
})
export class NavigationBar {
    constructor(private dataService: SpecmateDataService) { }

    private get version(): string {
        return Config.VERSION;
    }
}
