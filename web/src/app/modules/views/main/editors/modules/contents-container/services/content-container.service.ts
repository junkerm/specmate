import { EventEmitter, Injectable } from '@angular/core';

 @Injectable()
export class ContentsContainerService {

   public onModelDeleted = new EventEmitter<void>();

   constructor() { }

   public isDeleted() {
    this.onModelDeleted.emit();
}

}
