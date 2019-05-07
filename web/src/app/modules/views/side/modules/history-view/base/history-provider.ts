import { Folder } from '../../../../../../model/Folder';
import { History } from '../../../../../../model/History';
import { HistoryEntry } from '../../../../../../model/HistoryEntry';
import { IContainer } from '../../../../../../model/IContainer';
import { Type } from '../../../../../../util/type';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';

type HistoryType = 'single' | 'container' | 'recursive';

export class HistoryProvider {

    constructor(private dataService: SpecmateDataService) { }

    public async getHistory(element: IContainer): Promise<HistoryEntry[]> {
        const type = this.determineHistoryType(element);
        const history: History = await this.dataService.performQuery(element.url, 'history', { type: type });
        if (history === undefined) {
            return [];
        }
        return history.entries.filter(this.getFilter(element));
    }

    private determineHistoryType(element: IContainer): HistoryType {
        if (this.isSingle(element)) {
            return 'single';
        }
        return 'container';
    }

    private getFilter(element: IContainer): (historyEntry: HistoryEntry) => boolean {
        if (this.isSingle(element)) {
            return (historyEntry: HistoryEntry) => historyEntry.deletedObjects.length === 0;
        }
        return () => true;
    }

    private isSingle(element: IContainer): boolean {
        return Type.is(element, Folder);
    }
}
