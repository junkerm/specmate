import { EventEmitter, Injectable } from '@angular/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { Subject } from 'rxjs';

 @Injectable()
export class ContentsContainerService {

   private onModelDeleted = new Subject<string>();

   deleted$ = this.onModelDeleted.asObservable();

   constructor() { }

   public isDeleted() {
    this.onModelDeleted.next();
}

}
