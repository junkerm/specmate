import { EventEmitter, Injectable } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { Requirement } from '../../../../../../model/Requirement';
import { Type } from '../../../../../../util/type';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { AuthenticationService } from '../../../../main/authentication/modules/auth/services/authentication.service';

@Injectable()
export class SelectedElementService {
    private _selectedElements: IContainer[];

    private _selectionChanged: EventEmitter<IContainer[]>;

    constructor(navigator: NavigatorService, auth: AuthenticationService) {
        this.selectedElements = [];

        navigator.hasNavigated.subscribe((element: IContainer) => {
            if (this.isSelectable(element)) {
                this.select(element);
            } else {
                this.deselect();
            }
        });

        auth.authChanged.subscribe(() => this.deselect());
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

    public deselectElements(elements: IContainer[]) {
        let newSelection = this.selectedElements.filter( (elem: IContainer) => {
            return elements.indexOf(elem) < 0;
        });
        this.selectedElements = newSelection;
    }

    public deselectElement(element: IContainer) {
        this.deselectElements([element]);
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

    public toggleSelection(elements: IContainer[]): void {
        elements.forEach(element => {
            let ind = this.selectedElements.indexOf(element);
            if (ind > -1) {
                this.selectedElements.splice(ind, 1);
            } else {
                this.selectedElements.push(element);
            }
        });
        this.selectionChanged.emit(this.selectedElements);
    }

    public addToSelection(element: IContainer): void {
        if (!this.isSelected(element)) {
            let sel = this.selectedElements;
            sel.push(element);
            this.selectedElements = sel;
        }
    }

    private isSelectable(element: IContainer): boolean {
        return !Type.is(element, Requirement);
    }
}
