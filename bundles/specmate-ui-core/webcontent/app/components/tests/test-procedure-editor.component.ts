import {IContentElement} from '../../model/IContentElement';
import {TestParameter} from '../../model/TestParameter';
import {TestSpecification} from '../../model/TestSpecification';
import {Type} from '../../util/Type';
import {TestCase} from '../../model/TestCase';
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
export class TestProcedureEditor  implements OnInit {

    /** The test procedure being edited */
    testProcedure:TestProcedure;

    /** The contents of the test procedure */
    contents:IContainer[];

    /** The test case this test procedure belongs to */
    testCase: TestCase;

    /** The test case this test procedure belongs to */
    testSpecification: TestSpecification;

    testSpecContents:IContainer[];

    
    /** getter for the input parameters */
    get inputParameters(): IContentElement[] {
        return this.testSpecContents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "INPUT";
        });
    }

    /** getter for the output parameters */
    get outputParameters(): IContentElement[] {
        return this.testSpecContents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "OUTPUT";
        });
    }


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
                this.readTestCase();
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

    private readTestCase(){
        this.dataService.readElement(Url.parent(this.testProcedure.url)).then(
            (element:IContainer) => {
                if(Type.is(element,TestCase)){
                    this.testCase=<TestCase>element;
                    this.readTestCaseSpecification();
                }
            }
        )
    }

    private readTestCaseSpecification(){
        if(this.testCase){
             this.dataService.readContents(Url.parent(this.testCase.url)).then(
            (elements:IContainer[]) => {
                this.testSpecContents=elements;
            }
        )
        }
    }

}