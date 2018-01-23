import { ProcessStep } from '../../../../../../model/ProcessStep';
import { Type } from '../../../../../../util/type';
import { Injectable } from '@angular/core';
import { Config } from '../../../../../../config/config';
import { SelectedElementService } from '../../../../side/modules/selected-element/services/selected-element.service';
import { AdditionalInformationService } from '../../../../side/modules/links-actions/services/additional-information.service';
import { PaneItem } from '../../../../base/pane-item';
import { PropertiesEditor } from '../../../../side/modules/properties-editor/components/properties-editor.component';
import { TracingLinks } from '../../../../side/modules/tracing-links/components/tracing-links.component';
import { LinksActions } from '../../../../side/modules/links-actions/components/links-actions.component';

@Injectable()
export class ViewControllerService {

    private _isEditorMaximized = false;
    private _loggingOutputShown: boolean = Config.LOG_INITIALLY_SHOWN;

    public paneItems: PaneItem[] = [
        new PaneItem(PropertiesEditor),
        new PaneItem(TracingLinks),
        new PaneItem(LinksActions)
    ];

    public get projectExplorerShown(): boolean {
        return true;
    }

    public get loggingOutputShown(): boolean {
        return this._loggingOutputShown;
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
        return this.selectedElementService.hasSelection;
    }

    public get tracingLinksShown(): boolean {
        return Type.is(this.selectedElementService.selectedElement, ProcessStep);
    }

    public get linksActionsShown(): boolean {
        return this.additionalInformationService.hasAdditionalInformation;
    }

    constructor(private selectedElementService: SelectedElementService,
        private additionalInformationService: AdditionalInformationService) { }
}
