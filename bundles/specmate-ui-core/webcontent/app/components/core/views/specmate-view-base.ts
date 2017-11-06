import { IContainer } from '../../../model/IContainer';
import { Url } from '../../../util/Url';
import { EditorCommonControlService } from '../../../services/common-controls/editor-common-control.service';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { Params, ActivatedRoute } from '@angular/router';
import { NavigatorService } from '../../../services/navigation/navigator.service';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { OnInit } from '@angular/core';
import 'rxjs/add/operator/switchMap';

export abstract class SpecmateViewBase implements OnInit {

    protected abstract get isValid(): boolean;

    constructor(
        protected dataService: SpecmateDataService, 
        protected navigator: NavigatorService, 
        protected route: ActivatedRoute, 
        protected modal: ConfirmationModal, 
        protected editorCommonControlService: EditorCommonControlService) {}

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => {
                let url: string = Url.fromParams(params);
                if(Url.isRoot(url)) {
                    return this.dataService.readElement(url);
                }
                return this.dataService.readContents(Url.parent(url)).then(() => this.dataService.readElement(url));
            })
            .subscribe((element: IContainer) => {
                this.onElementResolved(element);
            });
    }

    ngDoCheck() {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
    }

    protected abstract onElementResolved(element: IContainer): void;
}