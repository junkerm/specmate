import { IContainer } from '../../../model/IContainer';
import { Url } from '../../../util/Url';
import { EditorCommonControlService } from '../../../services/common-controls/editor-common-control.service';
import { ConfirmationModal } from '../forms/confirmation-modal.service';
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
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((element: IContainer) => {
                this.onElementResolved(element);
            });
    }

    ngDoCheck() {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
    }

    protected abstract onElementResolved(element: IContainer): void;
}