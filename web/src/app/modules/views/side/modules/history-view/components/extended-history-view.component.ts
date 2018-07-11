import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { History } from '../../../../../../model/History';
import { HistoryEntry } from '../../../../../../model/HistoryEntry';
import { HistoryView } from './history-view.component';

@Component({
    moduleId: module.id.toString(),
    selector: 'extended-history-view',
    templateUrl: 'extended-history-view.component.html',
    styleUrls: ['extended-history-view.component.css']
})
export class ExtendedHistoryView extends HistoryView {}
