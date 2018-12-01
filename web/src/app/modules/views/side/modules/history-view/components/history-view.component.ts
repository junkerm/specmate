import { Component } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
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
        private dataService: SpecmateDataService) { }

    ngOnInit() {
        this.navigator.hasNavigated.subscribe(() => this.loadHistory());
        this.dataService.committed.subscribe(() => this.loadHistory());
    }

    private async loadHistory(): Promise<void> {
        if (this.navigator.currentElement === undefined) {
            return;
        }
        const history = await this.dataService.performQuery(this.navigator.currentElement.url, 'history', { type: 'container' });
        if (history !== undefined) {
            this.modelHistoryEntries = history.entries;
        } else {
            this.modelHistoryEntries = [];
        }
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
