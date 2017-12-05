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
import { DragulaService } from 'ng2-dragula';

export abstract class DraggableSupportingViewBase extends SpecmateViewBase {

    private element: IContainer;

    protected abstract get relevantElements(): (IContentElement & IPositionable)[];
    
    protected isDragging = false;
    
    /** The contents of the test specification */
    protected _contents: IContentElement[];

    public get contents(): IContentElement[] {
        return this._contents;
    }

    public set contents(contents: IContentElement[]) {
        this._contents = contents;
    }

    public get sortedContents(): IContentElement[] {
        return Sort.sortArray(this.contents);
    }
    
    constructor(
        dataService: SpecmateDataService, 
        navigator: NavigatorService, 
        route: ActivatedRoute, 
        modal: ConfirmationModal, 
        editorCommonControlService: EditorCommonControlService,
        private dragulaService: DragulaService) {
        super(dataService, navigator, route, modal, editorCommonControlService);

        this.dragulaService.dropModel.subscribe((value: any) => this.onDropModel(value.slice(1)));
    }

    private onDropModel(value: any): void {
        let [el, target, source] = value;
        this.sanitizeContentPositions(true);
    }

    protected sanitizeContentPositions(update: boolean): void {
        this.dataService.sanitizeContentPositions(this.relevantElements, update);
        Sort.sortArrayInPlace(this.contents);
    }

    public onElementResolved(element: IContainer): Promise<void> {
        this.element = element;
        return this.readContents();
    }

    /** Reads to the contents of the test specification  */
    protected readContents(): Promise<void> {
        if(!this.element) {
            return Promise.resolve();
        }
        return this.dataService.readContents(this.element.url)
            .then((contents: IContainer[]) => this.contents = contents as IContentElement[])
            .then(() => this.sanitizeContentPositions(true))
            .then(() => this.dataService.commit('Save (Sanitized positions)'))
    }
}