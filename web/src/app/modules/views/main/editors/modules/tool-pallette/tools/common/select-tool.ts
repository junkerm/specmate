import { IContainer } from '../../../../../../../../model/IContainer';
import { Id } from '../../../../../../../../util/id';
import { Key } from '../../../../../../../../util/keycode';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../services/multiselection.service';
import { GraphTransformer } from '../../util/graphTransformer';
import { IDragAndDropTool } from '../IDragAndDropTool';
import { IKeyboardTool } from '../IKeyboardTool';
import { ToolBase } from '../tool-base';
import { ClipboardService } from '../../services/clipboard-service';

export class SelectTool extends ToolBase implements IKeyboardTool, IDragAndDropTool {
    public icon = 'mouse-pointer';
    public color = 'primary';
    public cursor = 'default';
    public name = 'tools.select';
    public selectedElements: any[] = [];
    public done = false;

    public sticky = false;

    private static originType: string;

    public activate() {
        this.selectedElements = [];
    }

    public deactivate() {
        this.selectedElements = [];
    }

    public click(event: MouseEvent, zoom: number): Promise<void> {
        return Promise.resolve();
    }

    private setSelection(element: IContainer): void {
        this.selectedElements = [element];
        this.selectedElementService.selectedElements = this.selectedElements;
    }

    public select(element: IContainer, evt: MouseEvent): Promise<void> {
        if (evt.shiftKey) {
            if (!('x' in element && 'y' in element)) {
                // If the user selects an ISpecmatePositionableModelObject the selection is already handled by DraggableElementBase
                this.selectedElementService.toggleSelection([element]);
            }
        } else {
            if (this.selectedElementService.isSelected(element)) {
                return Promise.resolve();
            }
            this.setSelection(element);
        }

        let blur = (<HTMLElement>document.activeElement).blur;
        if (blur) {
            (<HTMLElement>document.activeElement).blur();
        }
        return Promise.resolve();
    }

    public mouseDown(event: MouseEvent, zoom: number): Promise<void> {
        this.rect.mouseDown(event, zoom);
        return Promise.resolve();
    }

    public mouseDrag(event: MouseEvent, zoom: number): Promise<void> {
        this.rect.mouseMove(event, zoom);
        return Promise.resolve();
    }

    public mouseUp(event: MouseEvent, zoom: number): Promise<void> {
        return this.rect.mouseUp(event, zoom);
    }

    private cancelEvent(evt: KeyboardEvent) {
        evt.preventDefault();
        evt.stopPropagation();
    }

    // Keyboard Shortcuts
    public keydown(evt: KeyboardEvent): Promise<void> {
        let ctrl = evt.ctrlKey || evt.metaKey;

        if (ctrl && evt.key === 'c') {
            // Copy
            this.cancelEvent(evt);
            return this.copySelection();
        }

        if (ctrl && evt.key === 'v') {
            // Paste
            this.cancelEvent(evt);
            return this.pasteSelection();
        }

        if (ctrl && evt.key === 'x') {
            // Cut
            this.cancelEvent(evt);
            return this.cutSelection();
        }

        if (evt.keyCode === Key.BACKSPACE) {
            // Delete
            this.cancelEvent(evt);
            return this.deleteSelection();
        }
        return Promise.resolve();
    }

    private async copySelection(): Promise<void> {
        // Copy Selection
        SelectTool.originType = this.model.className;
        let graphTransformer = new GraphTransformer(this.dataService, this.selectedElementService, this.model);
        let sel = this.selectedElementService.selectedElements.slice();
        this.clipboardService.clipboard = await graphTransformer.cloneSubgraph(sel, Id.uuid, false);
        return Promise.resolve();
    }

    private cutSelection(): Promise<void> {
        return this.copySelection().then(() => this.deleteSelection());
    }

    private async pasteSelection(): Promise<void> {
        if (!SelectTool.originType || !this.clipboardService.hasClipboard()) {
            // Nothing to paste
            return Promise.resolve();
        }

        let graphTransformer = new GraphTransformer(this.dataService, this.selectedElementService, this.model);
        let compoundId: string = Id.uuid;
        // Check Compatible Origin
        if (this.model.className !== SelectTool.originType) {
            return Promise.resolve();
        }

        let newSelection = await graphTransformer.cloneSubgraph(this.clipboardService.clipboard, compoundId, true);
        this.selectedElementService.selectedElements = newSelection;
        return Promise.resolve();
    }

    private async deleteSelection(): Promise<void> {
        let compoundId: string = Id.uuid;
        let graphTransformer = new GraphTransformer(this.dataService, this.selectedElementService, this.model);
        let selection = this.selectedElementService.selectedElements.slice();
        await graphTransformer.deleteAll(selection, compoundId);
        return Promise.resolve();
    }

    constructor(protected selectedElementService: SelectedElementService, private dataService: SpecmateDataService,
                private rect: MultiselectionService, private clipboardService: ClipboardService, private model: IContainer) {
        super(selectedElementService);
    }
}
