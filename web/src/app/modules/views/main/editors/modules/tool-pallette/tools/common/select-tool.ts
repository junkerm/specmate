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

export class SelectTool extends ToolBase implements IKeyboardTool, IDragAndDropTool {
    public icon = 'mouse-pointer';
    public color = 'primary';
    public cursor = 'default';
    public name = 'tools.select';
    public selectedElements: any[] = [];
    public done = false;

    public sticky = false;

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
            this.copySelection();
        }

        if (ctrl && evt.key === 'v') {
            // Paste
            this.cancelEvent(evt);
            this.pasteSelection();
        }

        if (ctrl && evt.key === 'x') {
            // Cut
            this.cancelEvent(evt);
            this.cutSelection();
        }

        if (evt.keyCode === Key.BACKSPACE) {
            // Delete
            this.cancelEvent(evt);
            this.deleteSelection(this.selectedElementService.selectedElements.slice());
        }
        return Promise.resolve();
    }

    private static origin: IContainer;
    private static cutFlag: boolean;
    private static selection: IContainer[];

    private copySelection(): void {
        SelectTool.origin = this.model;
        SelectTool.selection = this.selectedElementService.selectedElements.slice();
        SelectTool.cutFlag = false;
    }

    private cutSelection(): void {
        SelectTool.origin = this.model;
        SelectTool.selection = this.selectedElementService.selectedElements.slice();
        SelectTool.cutFlag = true;
    }

    private async pasteSelection(): Promise<void> {
        if (!SelectTool.origin || !SelectTool.selection) {
            // Nothing to paste
            return Promise.resolve();
        }

        if (SelectTool.origin === this.model && SelectTool.cutFlag) {
            // Cut & Paste into the same model leaves the model the same
            return Promise.resolve();
        }

        let graphTransformer = new GraphTransformer(this.dataService, this.selectedElementService, this.model);
        let compoundId: string = Id.uuid;
        // Check Compatible Origin
        if (this.model.className !== SelectTool.origin.className) {
            return Promise.resolve();
        }

        let newSelection = await graphTransformer.createSubgraph(SelectTool.selection, compoundId);
        if (SelectTool.cutFlag) {
            let oldTransformer = new GraphTransformer(this.dataService, this.selectedElementService, SelectTool.origin);
            return oldTransformer.deleteAll(SelectTool.selection, compoundId);
        }
        this.selectedElementService.selectedElements = newSelection;
        return Promise.resolve();
    }

    private deleteSelection(selection?: IContainer[]): Promise<void> {
        let tmpSel: IContainer[] = [];
        if (selection) {
            // Delete can be pressed after we have selected some other nodes so we have to safe the seleciton.
            tmpSel = SelectTool.selection;
            SelectTool.selection = selection;
        }
        let compoundId: string = Id.uuid;
        let graphTransformer = new GraphTransformer(this.dataService, this.selectedElementService, this.model);
        let ret = graphTransformer.deleteAll(SelectTool.selection, compoundId);
        SelectTool.selection = tmpSel;
        return ret;
    }

    constructor(protected selectedElementService: SelectedElementService, private dataService: SpecmateDataService,
                private rect: MultiselectionService, private model: IContainer) {
        super(selectedElementService);
    }
}
