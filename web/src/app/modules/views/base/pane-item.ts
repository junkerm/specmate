import { Type } from '@angular/core';
import { ViewPane } from './view-pane';

export class PaneItem {
  constructor(public component: Type<ViewPane>) {}
}
