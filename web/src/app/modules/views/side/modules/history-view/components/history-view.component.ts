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
    template: '',
    selector: 'history-view'
})
export class HistoryView {
    public modelHistoryEntries: HistoryEntry[];

    public isCollapsed = true;

    constructor(
        private navigator: NavigatorService,
        private dataService: SpecmateDataService) {}

    ngOnInit() {
      this.navigator.hasNavigated.subscribe((elem: IContainer) => {
        this.dataService.performQuery(elem.url, 'historyRecursive', {})
        .then((history: History) => {
            if (history !== undefined) {
                this.modelHistoryEntries = history.entries;
            } else {
                this.modelHistoryEntries = [];
            }
        });
      });
    }

    public formatDate(timestamp: string): string {
        // jax-rs serializes long as string, so we need to convert here
        let date: Date = new Date(Number(timestamp));
        let year: number = date.getFullYear();
        let month: number = date.getMonth() + 1;
        let day: number = date.getDate();
        let hours: number = date.getHours();
        let minutes: number = date.getMinutes();
        let seconds: number = date.getSeconds();

        let tmp = year + '-'
          + ((month < 10) ? '0' : '') + month + '-'
          + ((day < 10) ? '0' : '') + day + ' '
          + ((hours < 10) ? '0' : '') + hours + ':'
          + ((minutes < 10) ? '0' : '') + minutes + ':'
          + ((seconds < 10) ? '0' : '') + seconds;
        return tmp;
    }
}
