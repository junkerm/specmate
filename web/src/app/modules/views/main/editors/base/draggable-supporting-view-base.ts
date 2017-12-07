import { SpecmateViewBase } from './specmate-view-base';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { IPositionable } from '../../../../../model/IPositionable';
import { Sort } from '../../../../../util/sort';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { EditorCommonControlService } from '../../../../actions/modules/common-controls/services/common-control.service';
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
        if (!this.element) {
            return Promise.resolve();
        }
        return this.dataService.readContents(this.element.url)
            .then((contents: IContainer[]) => this.contents = contents as IContentElement[])
            .then(() => this.sanitizeContentPositions(true))
            .then(() => this.dataService.commit('Save (Sanitized positions)'));
    }
}
