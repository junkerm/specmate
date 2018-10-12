import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Id } from '../../../../../../../util/id';
import { OnInit, Input } from '@angular/core';

export abstract class ContentContainerBase<T extends IContainer> implements OnInit {

    protected abstract get condition(): (element: IContainer) => boolean;
    protected contents: IContainer[];

    private _parent: IContainer;

    protected get parent(): IContainer {
        return this._parent;
    }

    @Input()
    protected set parent(parent: IContainer) {
        this._parent = parent;
        this.readContents();
    }

    constructor(
        protected dataService: SpecmateDataService,
        protected navigator: NavigatorService,
        protected translate: TranslateService,
        protected modal: ConfirmationModal) {
    }

    public async ngOnInit(): Promise<void> {
        await this.readContents();
    }

    public async create(name: string): Promise<void> {
        if (name) {
            const element = await this.createElement(name);
            this.navigator.navigate(element);
        }
    }

    public abstract async createElement(name: string): Promise<T>;

    public async delete(element: T,
        message: string = this.translate.instant('doYouReallyWantToDelete', { name: element.name })): Promise<void> {
        try {
            await this.modal.openOkCancel('ConfirmationRequired', message);
            await this.dataService.deleteElement(element.url, false, Id.uuid);
            await this.dataService.commit(this.translate.instant('delete'));
            await this.readContents();
        } catch (e) { }
    }

    public async duplicate(element: IContainer): Promise<void> {
        await this.dataService.performOperations(element.url, 'duplicate');
        await this.dataService.commit(this.translate.instant('duplicate'));
        await this.readContents();
    }

    protected async readContents(): Promise<void> {
        this.contents = undefined;
        const contents = await this.dataService.readContents(this.parent.url, false);
        this.contents = contents.filter(this.condition);
    }
}
