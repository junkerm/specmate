import { Component } from '@angular/core';
import { HistoryView } from './history-view.component';

@Component({
    moduleId: module.id.toString(),
    selector: 'extended-history-view',
    templateUrl: 'extended-history-view.component.html',
    styleUrls: ['extended-history-view.component.css']
})
export class ExtendedHistoryView extends HistoryView {}
