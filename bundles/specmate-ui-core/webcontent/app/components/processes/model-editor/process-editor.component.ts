import { Component, Input } from '@angular/core';
import { IContainer } from '../../../model/IContainer';

@Component({
    moduleId: module.id,
    selector: 'process-editor',
    templateUrl: 'process-editor.component.html',
    styleUrls: ['process-editor.component.css']
})
export class ProcessEditor {
    
    @Input()
    public contents: IContainer[];

}
