import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ViewControllerService } from '../../../../controller/modules/view-controller/services/view-controller.service';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { Command } from '../../../../../data/modules/data-service/services/command';
import { IContainer } from '../../../../../../model/IContainer';

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
        return command.changedFields.map((field: string) => field + '=' + element[field]).join(', ');
    }

    public getChangedFields(command: Command): string {
        return command.changedFields.join(', ');
    }
}
