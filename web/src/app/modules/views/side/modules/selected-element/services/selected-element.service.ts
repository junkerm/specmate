import { Injectable, EventEmitter } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { Type } from '../../../../../../util/type';
import { Requirement } from '../../../../../../model/Requirement';

@Injectable()
export class SelectedElementService {
    private _selectedElements: IContainer[];

    private _selectionChanged: EventEmitter<IContainer[]>;

    constructor(private navigator: NavigatorService) {
        this.selectedElements = [];

        navigator.hasNavigated.subscribe((element: IContainer) => {
            if (this.isSelectable(element)) {
                this.select(element);
            } else {
                this.deselect();
            }
        });
    }

    public get selectionChanged(): EventEmitter<IContainer[]> {
        if (!this._selectionChanged) {
            this._selectionChanged = new EventEmitter<IContainer[]>();
        }
        return this._selectionChanged;
    }

    public get hasSelection(): boolean {
        return this._selectedElements.length > 0;
    }

    public isSelected(element: IContainer): boolean {
        if (!element) {
            return false;
        }
        return this._selectedElements.some(e => e.url === element.url);
    }

    public get selectedElements(): IContainer[] {
        return this._selectedElements;
    }

    public set selectedElements(selectedElements: IContainer[]) {
        if (selectedElements === undefined) {
            selectedElements = [];
        }
        this._selectedElements = selectedElements;
        this.selectionChanged.emit(this.selectedElements);
    }

    public get selectedElement(): IContainer {
        if (this.hasSelection) {
            return this.selectedElements[0];
        }
        return undefined;
    }

    public set selectedElement(element: IContainer) {
        this.selectedElements = [element];
    }

    public deselect(): void {
        this.selectAll([]);
    }

    public selectAll(elements: IContainer[]): void {
        elements = elements.filter(e => this.isSelectable(e));
        this.selectedElements = elements;
    }

    public select(element: IContainer): void {
        this.selectAll([element]);
    }

    private isSelectable(element: IContainer): boolean {
        return !Type.is(element, Requirement);
    }
}
