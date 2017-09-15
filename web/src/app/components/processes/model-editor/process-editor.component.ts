import { Component, Input } from '@angular/core';
import { IContainer } from '../../../model/IContainer';
import { GraphicalEditorBase } from "../../core/graphical/graphical-editor-base";
import { ISpecmateModelObject } from "../../../model/ISpecmateModelObject";
import { ITool } from "../../core/graphical/tools/i-tool";

@Component({
    moduleId: module.id,
    selector: 'process-editor',
    templateUrl: 'process-editor.component.html',
    styleUrls: ['process-editor.component.css']
})
export class ProcessEditor extends GraphicalEditorBase {
    
    @Input()
    public contents: IContainer[];

    public get isValid(): boolean {
        return true;
    }

    public get tools(): ITool<ISpecmateModelObject>[] {
        return [];
    }

}
