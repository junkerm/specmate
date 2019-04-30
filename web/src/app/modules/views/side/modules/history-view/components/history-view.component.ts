import { Component } from '@angular/core';
import { HistoryEntry } from '../../../../../../model/HistoryEntry';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { HistoryProvider } from '../base/history-provider';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'history-view.component.html',
    styleUrls: ['history-view.component.css'],
    selector: 'history-view'
})
export class HistoryView {

    public modelHistoryEntries: HistoryEntry[];
    public isCollapsed = true;

    private historyProvider: HistoryProvider;

    constructor(
        private navigator: NavigatorService,
        private dataService: SpecmateDataService) {
        this.historyProvider = new HistoryProvider(dataService);
    }

    ngOnInit() {
        this.navigator.hasNavigated.subscribe(() => this.loadHistory());
        this.dataService.committed.subscribe(() => this.loadHistory());
    }

    private async loadHistory(): Promise<void> {
        if (this.navigator.currentElement === undefined) {
            return;
        }
        this.modelHistoryEntries = await this.historyProvider.getHistory(this.navigator.currentElement);
    }

    public getDeletedObjectName(s: string): string {
        return s.split('|')[0];
    }

    public getDeletedObjectType(s: string): string {
        let data = s.split('|');
        if (data.length == 2) {
            return '(' + data[1] + ')';
        }

        return '';
    }

    public getDate(timestamp: string): Date {
        // jax-rs serializes long as string, so we need to convert here
        return new Date(Number(timestamp));
    }
}
