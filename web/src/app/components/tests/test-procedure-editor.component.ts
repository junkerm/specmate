import {Url} from '../../util/Url';
import {IContainer} from '../../model/IContainer';
import { TestProcedure} from '../../model/TestProcedure';
import {Params, ActivatedRoute} from '@angular/router';
import {EditorCommonControlService} from '../../services/editor-common-control.service';
import {SpecmateDataService} from '../../services/specmate-data.service';
import { OnInit, Component } from '@angular/core';
@Component({
    moduleId: module.id,
    selector: 'test-procedure-editor',
    templateUrl: 'test-procedure-editor.component.html',
    //styleUrls: ['test-procedure-editor.component.css']
})
export class TestProcedureEditor implements OnInit {

    /** The test procedure being edited */
    testProcedure:TestProcedure;

    /** The contents of the test procedure */
    contents:IContainer[];

    /** Constructor */
    constructor(
        private dataService:SpecmateDataService,
        private route: ActivatedRoute,
        private editorCommonControlService: EditorCommonControlService
        ){}

    ngOnInit() {
        this.editorCommonControlService.showCommonControls = true;
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((testProcedure: IContainer) => {
                this.testProcedure = testProcedure as TestProcedure;
                this.readContents();
            });
    }

    /** Rads to the contents of the test specification  */
    private readContents(): void {
        if (this.testProcedure) {
            this.dataService.readContents(this.testProcedure.url).then((
                contents: IContainer[]) => {
                this.contents = contents;
            });
        }
    }

}