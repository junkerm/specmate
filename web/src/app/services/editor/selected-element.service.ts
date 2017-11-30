import { Injectable, EventEmitter } from '@angular/core';
import { SpecmateDataService } from '../data/specmate-data.service';
import { NavigatorService } from '../navigation/navigator.service';
import { ToolProvider } from '../../components/core/graphical/providers/tool-provider';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/Type';
import { Requirement } from '../../model/Requirement';


@Injectable()
export class SelectedElementService {
    private _selectedElement: IContainer;
    
    private _selectionChanged: EventEmitter<IContainer>;

    constructor(private navigator: NavigatorService) {
        navigator.hasNavigated.subscribe((element: IContainer) => {
            if(this.isSelectable(element)) {
                this.select(element);
            } else {
                this.deselect();
            }
        });
    }

    public get selectionChanged(): EventEmitter<IContainer> {
        if(!this._selectionChanged) {
            this._selectionChanged = new EventEmitter<IContainer>();
        }
        return this._selectionChanged;
    }

    public get hasSelection(): boolean {
        return this.selectedElement !== undefined;
    }

    public isSelected(element: IContainer): boolean {
        return this.selectedElement === element;
    }

    public get selectedElement(): IContainer {
        return this._selectedElement;
    }

    public set selectedElement(selectedElement: IContainer) {
        this._selectedElement = selectedElement;
        this.selectionChanged.emit(this.selectedElement);
    }

    public deselect(): void {
        this.selectedElement = undefined;
    }

    public select(element: IContainer): void {
        this.selectedElement = element;
    }

    private isSelectable(element: IContainer): boolean {
        return !Type.is(element, Requirement);
    }
}
