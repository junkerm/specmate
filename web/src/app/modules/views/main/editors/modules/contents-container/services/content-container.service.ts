import { EventEmitter, Injectable, Output } from '@angular/core';

@Injectable()
export class ContentsContainerService {

    @Output() onModelDeleted: EventEmitter<void> = new EventEmitter<void>();

    constructor() { }

    public isDeleted() {
        this.onModelDeleted.emit();
    }

}
