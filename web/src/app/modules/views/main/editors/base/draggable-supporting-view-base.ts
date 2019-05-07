import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DragulaService } from 'ng2-dragula';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { IPositionable } from '../../../../../model/IPositionable';
import { Sort } from '../../../../../util/sort';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { SpecmateViewBase } from './specmate-view-base';


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

    public dndBagName = 'DND_BAG';

    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        private dragulaService: DragulaService,
        translate: TranslateService) {
        super(dataService, navigator, route, modal, translate);
        this.dragulaService.dropModel(this.dndBagName)
            .subscribe(({sourceIndex, targetIndex}) => this.onDropModel(sourceIndex, targetIndex));
    }

    private onDropModel(sourceIndex: number, targetIndex: number): void {
        const source = this.relevantElements[sourceIndex];
        const target = this.relevantElements[targetIndex];
        const sourceContentIndex = this.contents.indexOf(source);
        const targetContentIndex = this.contents.indexOf(target);
        this.contents.splice(targetContentIndex, 0, this.contents.splice(sourceContentIndex, 1)[0]);
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
            .then(() => this.dataService.commit(this.translate.instant('save')));
    }
}
