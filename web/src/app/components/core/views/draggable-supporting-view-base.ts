import { ActivatedRoute } from '@angular/router';
import { SpecmateViewBase } from "./specmate-view-base";
import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { NavigatorService } from "../../../services/navigation/navigator.service";
import { ConfirmationModal } from "../../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from "../../../services/common-controls/editor-common-control.service";
import { IPositionable } from "../../../model/IPositionable";
import { IContainer } from "../../../model/IContainer";
import { Type } from "../../../util/Type";
import { Id } from "../../../util/Id";
import { IContentElement } from "../../../model/IContentElement";
import { Sort } from "../../../util/Sort";

export abstract class DraggableSupportingViewBase extends SpecmateViewBase {

    private element: IContainer;

    protected abstract get relevantElements(): (IContentElement & IPositionable)[];
    
    protected isDragging = false;
    
    /** The contents of the test specification */
    private _contents: IContentElement[];
    
    public get contents(): IContentElement[] {
        if(!this._contents) {
            return undefined;
        }
        if(!this.isDragging) {
            Sort.sortArrayInPlace(this._contents);
        }
        return this._contents;
    }
    
    constructor(
        dataService: SpecmateDataService, 
        navigator: NavigatorService, 
        route: ActivatedRoute, 
        modal: ConfirmationModal, 
        editorCommonControlService: EditorCommonControlService) {
        super(dataService, navigator, route, modal, editorCommonControlService);
    }

    public onDragStart(e: any): void {
        this.isDragging = true;
    }

    public onDropSuccess(e: any): void {
        this.sanitizeContentPositions(true);
        this.isDragging = false;
    }

    private sanitizeContentPositions(update: boolean): void {
        let compoundId: string = Id.uuid;
        this.relevantElements.forEach((element: IContainer & IPositionable, index: number) => {
            element.position = index;
            if(update) {
                this.dataService.updateElement(<IContainer>element, true, compoundId);
            }
        });
    }

    public onElementResolved(element: IContainer): void {
        this.element = element;
        this.readContents();
    }

    /** Reads to the contents of the test specification  */
    protected readContents(): void {
        if (this.element) {
            this.dataService.readContents(this.element.url).then((contents: IContainer[]) => {
                this._contents = contents;
                this.sanitizeContentPositions(true);
                this.dataService.commit('Save (Sanitized positions)');
            });
        }
    }
}