import { Component } from '@angular/core';
import { HistoryView } from './history-view.component';

@Component({
    moduleId: module.id.toString(),
    selector: 'simple-history-view',
    templateUrl: 'simple-history-view.component.html',
    styleUrls: ['simple-history-view.component.css']
})
export class SimpleHistoryView extends HistoryView {}
