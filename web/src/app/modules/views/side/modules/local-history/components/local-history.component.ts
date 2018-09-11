import { Component } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { Command } from '../../../../../data/modules/data-service/services/command';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'local-history',
    templateUrl: 'local-history.component.html'
})
export class LocalHistory {
    constructor(private dataService: SpecmateDataService) { }

    public get commands(): Command[] {
        return this.dataService.unresolvedCommands;
    }

    public getChangedFieldValues(command: Command, version: ('new' | 'original')): string {
        const element: IContainer = version === 'new' ? command.newValue : command.originalValue;
        let changedFields: string[] = command.changedFields;
        if (changedFields === undefined) {
            return undefined;
        }
        return changedFields.map((field: string) => field + '=' + element[field]).join(', ');
    }

    public getChangedFields(command: Command): string {
        let changedFields: string[] = command.changedFields;
        if (changedFields === undefined) {
            return undefined;
        }
        return changedFields.join(', ');
    }
}
