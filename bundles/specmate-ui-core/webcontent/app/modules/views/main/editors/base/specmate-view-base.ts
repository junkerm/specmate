import 'rxjs/add/operator/switchMap';
import { OnInit } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute, Params } from '@angular/router';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { EditorCommonControlService } from '../../../../actions/modules/common-controls/services/common-control.service';
import { Url } from '../../../../../util/url';
import { IContainer } from '../../../../../model/IContainer';

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
                return this.dataService.readElement(Url.fromParams(params));
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