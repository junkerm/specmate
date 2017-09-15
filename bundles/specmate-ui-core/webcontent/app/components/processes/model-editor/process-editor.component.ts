import { Component, Input } from '@angular/core';
import { IContainer } from '../../../model/IContainer';
import { GraphicalEditor } from "../../core/graphical/graphical-editor";
import { ITool } from "../../core/graphical/i-tool";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";

@Component({
    moduleId: module.id,
    selector: 'process-editor',
    templateUrl: 'process-editor.component.html',
    styleUrls: ['process-editor.component.css']
})
export class ProcessEditor extends GraphicalEditor {
    
    @Input()
    public contents: IContainer[];

    public get isValid(): boolean {
        return true;
    }

    public get tools(): ITool<ISpecmateModelObject>[] {
        return [];
    }

}
