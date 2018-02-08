import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { History } from '../../../../../../model/History';
import { HistoryEntry } from '../../../../../../model/HistoryEntry';

@Component({
    moduleId: module.id.toString(),
    selector: 'history-view',
    templateUrl: 'history-view.component.html',
    styleUrls: ['history-view.component.css']
})
export class HistoryView {
    private modelHistoryEntries: HistoryEntry[];

    constructor(
        private navigator: NavigatorService,
        private dataService: SpecmateDataService) {
            this.navigator.hasNavigated.subscribe((elem: IContainer) => {
                this.dataService.performQuery(elem.url, 'historyRecursive', {})
                .then((history: History) => this.modelHistoryEntries = history.entries);
        });
    }
}
