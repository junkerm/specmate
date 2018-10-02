import { ProcessStep } from '../../../../../../model/ProcessStep';
import { Type } from '../../../../../../util/type';
import { Injectable } from '@angular/core';
import { Config } from '../../../../../../config/config';
import { SelectedElementService } from '../../../../side/modules/selected-element/services/selected-element.service';
import { AdditionalInformationService } from '../../../../side/modules/links-actions/services/additional-information.service';
import { AuthenticationService } from '../../../../main/authentication/modules/auth/services/authentication.service';
import { Folder } from '../../../../../../model/Folder';

@Injectable()
export class ViewControllerService {

    private _isEditorMaximized = false;
    private _loggingOutputShown: boolean = Config.LOG_INITIALLY_SHOWN;

    public get isLoggedIn(): boolean {
        return this.auth.isAuthenticated;
    }

    public get navigationShown(): boolean {
        return this.isLoggedIn && true;
    }

    public get projectExplorerShown(): boolean {
        return this.isLoggedIn && true;
    }

    public get historyShown(): boolean {
        return this.isLoggedIn;
    }

    public get loggingOutputShown(): boolean {
        return this.isLoggedIn && this._loggingOutputShown;
    }
    public set loggingOutputShown(loggingOutputShown: boolean) {
        this._loggingOutputShown = loggingOutputShown;
    }

    public showLoggingOutput(): void {
        this.loggingOutputShown = true;
    }

    public hideLoggingOutput(): void {
        this.loggingOutputShown = false;
    }

    public maximizeEditor(): void {
        this._isEditorMaximized = true;
    }

    public unmaximizeEditor(): void {
        this._isEditorMaximized = false;
    }

    public get isEditorMaximized(): boolean {
        return this._isEditorMaximized;
    }

    public get propertiesShown(): boolean {
        let sel = this.selectedElementService.selectedElement;
        let notFolder = !Type.is(sel, Folder);
        return this.isLoggedIn && this.selectedElementService.hasSelection && notFolder;
    }

    public get tracingLinksShown(): boolean {
        return this.isLoggedIn && Type.is(this.selectedElementService.selectedElement, ProcessStep);
    }

    public get linksActionsShown(): boolean {
        return this.isLoggedIn && this.additionalInformationService.hasAdditionalInformation;
    }

    constructor(
        private selectedElementService: SelectedElementService,
        private additionalInformationService: AdditionalInformationService,
        private auth: AuthenticationService) { }
}
