import { Injectable } from '@angular/core';
import { Config } from '../../../../../../config/config';
import { Folder } from '../../../../../../model/Folder';
import { ProcessStep } from '../../../../../../model/ProcessStep';
import { Type } from '../../../../../../util/type';
import { AuthenticationService } from '../../../../main/authentication/modules/auth/services/authentication.service';
import { AdditionalInformationService } from '../../../../side/modules/links-actions/services/additional-information.service';
import { SelectedElementService } from '../../../../side/modules/selected-element/services/selected-element.service';

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
        return this.isLoggedIn && this.selectedElementService.hasSelection && !this.isTopLibraryFolder;
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
        return this.isLoggedIn && this.selectedElementService.hasSelection && !this.isTopLibraryFolder;
    }

    public get tracingLinksShown(): boolean {
        let selected = this.selectedElementService.selectedElement;
        if (this.isLoggedIn && selected !== undefined) {
            if (Type.is(selected, ProcessStep) && selected['tracesTo']) {
                return true;
            }
        }

        return false;
    }

    public get linksActionsShown(): boolean {
        return this.isLoggedIn && this.additionalInformationService.hasAdditionalInformation;
    }

    public get areFolderPropertiesEditable(): boolean {
        return !this.isTopLibraryFolder;
    }

    private get isTopLibraryFolder(): boolean {
        let selected = this.selectedElementService.selectedElement;
        if (Type.is(selected, Folder)) {
            return this.auth.token.libraryFolders.indexOf(selected.id) > -1;
        }
        return false;
    }

    constructor(
        private selectedElementService: SelectedElementService,
        private additionalInformationService: AdditionalInformationService,
        private auth: AuthenticationService) { }
}
